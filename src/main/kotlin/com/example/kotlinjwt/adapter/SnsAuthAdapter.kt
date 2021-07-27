package com.example.kotlinjwt.adapter

interface SnsAuthAdapter {
    fun verifyAccessToken(accessToken: String, snsId: String ): Boolean
}