package com.example.kotlinjwt.public

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class PublicHandler {
    suspend fun test(request: ServerRequest): ServerResponse {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait("토큰 확인~!")
    }
}