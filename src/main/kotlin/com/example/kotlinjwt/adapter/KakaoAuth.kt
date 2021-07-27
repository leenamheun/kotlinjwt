package com.example.kotlinjwt.adapter

import com.example.kotlinjwt.common.config.AppProperties
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate


@Component
class KakaoAuth(
    val appProperties: AppProperties,
    val restTemplate: RestTemplate,
) : SnsAuthAdapter {
    override fun verifyAccessToken(accessToken: String, snsId: String): Boolean {
        val headers = HttpHeaders()
        headers.setBearerAuth(accessToken)
        val httpEntity = HttpEntity<Map<*, *>>(headers)
        var resUserToken = restTemplate.exchange(
            "${appProperties.kakaoEndpoint}/v1/user/access_token_info",
            HttpMethod.GET,
            httpEntity,
            KakaoUserAccessToken::class.java
        ).body ?: return false
        return snsId == resUserToken.id && appProperties.kakaoAppId == resUserToken.appId
    }

}

data class KakaoUserAccessToken(
    val id: String,
    val expiresInMillis: String,
    val appId: String,
)
