package com.example.kotlinjwt.public

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class PublicRoute {
    val basePath = "/api/v1/public"

    @Bean
    fun publicRouter(handler: PublicHandler) = coRouter {
        path(basePath).nest {
            GET("", handler::test)
        }
    }
}