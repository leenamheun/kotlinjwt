package com.example.kotlinjwt.common.config

import com.example.kotlinjwt.common.JWTService
import com.example.kotlinjwt.common.config.basic.BasicAuthenticationSuccessHandler
import com.example.kotlinjwt.common.config.basic.CustomerReactiveUserDetailsService
import com.example.kotlinjwt.common.config.bearer.BearerTokenReactiveAuthenticationManager
import com.example.kotlinjwt.common.config.bearer.ServerHttpBearerAuthenticationConverter
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig(private val jwtService: JWTService) {
    companion object {
        val EXCLUDED_PATHS = arrayOf(
            "/api/v1/auth/signinsns"
        )
    }

    @Bean
    fun configureSecurity(
        http: ServerHttpSecurity,
        basicAuthenticationFilter: AuthenticationWebFilter,
    ): SecurityWebFilterChain {
        return http
            .cors().and()
            .csrf().disable()
            .logout().disable()
            .authorizeExchange()
            .pathMatchers(*EXCLUDED_PATHS).permitAll()
            .and()
            .authorizeExchange()
            .pathMatchers("/api/v1/auth/signin")
            .authenticated()
            .and()
            .addFilterAt(basicAuthenticationFilter, SecurityWebFiltersOrder.HTTP_BASIC)
            .authorizeExchange()
            .pathMatchers("/api/**")
            .authenticated()
            .and()
            .addFilterAt(bearerAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun basicAuthenticationFilter(
        reactiveUserDetailsService: CustomerReactiveUserDetailsService,
    ): AuthenticationWebFilter {
        val authManager =UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService)
        authManager.setPasswordEncoder(passwordEncoder())

        val successHandler = BasicAuthenticationSuccessHandler(jwtService)
        val basicAuthenticationFilter = AuthenticationWebFilter(authManager)
        basicAuthenticationFilter.setAuthenticationSuccessHandler(successHandler)
        basicAuthenticationFilter.setRequiresAuthenticationMatcher { ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET, "/api/v1/auth/signin").matches(it) }
        return basicAuthenticationFilter
    }

    @Bean
    fun bearerAuthenticationFilter(): AuthenticationWebFilter {
        val authManager = BearerTokenReactiveAuthenticationManager()
        val bearerAuthenticationFilter = AuthenticationWebFilter(authManager)
        bearerAuthenticationFilter.setServerAuthenticationConverter(ServerHttpBearerAuthenticationConverter(jwtService))
        bearerAuthenticationFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/api/**"))
        return bearerAuthenticationFilter
    }
}