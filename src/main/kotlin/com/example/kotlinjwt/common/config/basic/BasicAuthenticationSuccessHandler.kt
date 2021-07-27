package com.example.kotlinjwt.common.config.basic

import com.example.kotlinjwt.common.JWTService
import kotlinx.coroutines.reactor.mono
import org.springframework.http.HttpHeaders
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class BasicAuthenticationSuccessHandler(
    private val jwtService: JWTService,
) : ServerAuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        webFilterExchange: WebFilterExchange?,
        authentication: Authentication?,
    ): Mono<Void> = mono {
        val principal = authentication?.principal
        when (principal) {
            is User -> {
                val roles = principal.authorities.map { it.authority }.toTypedArray()
                val accessToken = jwtService.accessToken(principal.username, roles)
                val refreshToken = jwtService.refreshToken(principal.username, roles)

                webFilterExchange?.exchange?.response?.headers?.set("Authorization", accessToken)
                webFilterExchange?.exchange?.response?.headers?.set("JWT-Refresh-Token", refreshToken)
                webFilterExchange?.exchange?.response?.headers?.set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
                    "${HttpHeaders.AUTHORIZATION}, JWT-Refresh-Token")
            }
        }
        return@mono null
    }
}