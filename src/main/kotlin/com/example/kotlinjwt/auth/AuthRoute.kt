package com.example.kotlinjwt.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class AuthRoute {
    val basePath = "/api/v1/auth"

    @Bean
    fun authRouter(handler: AuthHandler) = coRouter {
        path(basePath).nest{
            POST("/signinsns", handler::signinSns)
        }
    }
}