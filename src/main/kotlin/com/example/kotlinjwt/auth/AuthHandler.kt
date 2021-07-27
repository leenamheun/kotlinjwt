package com.example.kotlinjwt.auth

import com.example.kotlinjwt.auth.service.AuthService
import com.example.kotlinjwt.member.SigninSnsReq
import com.example.kotlinjwt.member.service.MemberService
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import java.lang.Exception

@Component
class AuthHandler(
    private val authService: AuthService,
    private val memberService: MemberService
) {
    suspend fun signinSns(request: ServerRequest): ServerResponse {
        val signinReq = request.awaitBody<SigninSnsReq>()
        return if (authService.verifyAccessToken(signinReq.snsToken, signinReq.snsType, signinReq.snsId)) {
            val member = memberService.signinSns(signinReq)
            ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(authService.getAccessToken(member.id!!))
        }else{
            //throw SigninSnsException()
            throw Exception();
        }
    }
}