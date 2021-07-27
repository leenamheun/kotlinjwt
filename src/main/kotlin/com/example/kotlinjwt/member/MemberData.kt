package com.example.kotlinjwt.member

import com.example.kotlinjwt.enum.SnsType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class SigninSnsReq(
    @get:NotBlank
    var snsId: String,
    @field:NotNull
    var snsType: SnsType,
    var snsToken: String = "",
    var username: String? = "",
    var email: String? = "",
    var nickName: String? = "",
    //var gender: Gender? = null
)

data class SignupRes(
    var id: Long = -1,
    var isCert: Boolean = false
)