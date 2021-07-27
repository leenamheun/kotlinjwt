package com.example.kotlinjwt.common.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AppProperties {

    @Value("\${sns.kakao.app.id}")
    lateinit var kakaoAppId: String

    @Value("\${sns.kakao.endpoint}")
    lateinit var kakaoEndpoint: String

    @Value("\${sns.google.app.id.and}")
    lateinit var googleAppIdAnd: String

    @Value("\${sns.google.app.id.ios}")
    lateinit var googleAppIdIos: String
}