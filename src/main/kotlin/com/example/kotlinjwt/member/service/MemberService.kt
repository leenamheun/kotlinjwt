package com.example.kotlinjwt.member.service

import com.example.kotlinjwt.member.SigninSnsReq
import com.example.kotlinjwt.member.SignupRes

interface MemberService {
    fun signinSns(signinSnsReq: SigninSnsReq): SignupRes
}