package com.example.kotlinjwt.auth.service

import com.example.kotlinjwt.auth.service.impl.TokenRes
import com.example.kotlinjwt.enum.SnsType

interface AuthService {
    fun getAccessToken(memberId: Long): TokenRes
    fun verifyAccessToken(accessToken: String, snsType: SnsType, snsId: String): Boolean
}