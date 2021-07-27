package com.example.kotlinjwt.auth.service.impl

import com.example.kotlinjwt.adapter.GoogleAuth
import com.example.kotlinjwt.adapter.KakaoAuth
import com.example.kotlinjwt.auth.service.AuthService
import com.example.kotlinjwt.common.JWTService
import com.example.kotlinjwt.enum.SnsType
import com.example.kotlinjwt.member.repository.MemberInfoRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Component
class AuthServiceImpl(
    val kakaoAuth: KakaoAuth,
    val googleAuth: GoogleAuth,
    val memberInfoRepository: MemberInfoRepository,
    val jwtService: JWTService,
) : AuthService {
    @Transactional(readOnly = true)
    override fun getAccessToken(memberId: Long): TokenRes {
        val member = memberInfoRepository.findByIdAndActive(memberId, true)
            ?: throw EntityNotFoundException("not found a Member(id = ${memberId})")
        val authorities = member.roles.map { memberRole -> memberRole.role.roleType.name }.toTypedArray()

        //토큰 발행
        val token: String = jwtService.accessToken(member.id.toString(), authorities)
        return TokenRes(token, member.snsId)
    }

    override fun verifyAccessToken(accessToken: String, snsType: SnsType, snsId: String): Boolean =
        when (snsType) {
            SnsType.EMAIL -> true
            SnsType.KAKAO -> kakaoAuth.verifyAccessToken(accessToken, snsId)
            SnsType.GOOGLE -> googleAuth.verifyAccessToken(accessToken, snsId)
            else -> throw Exception()
            //throw SigninSnsException(ApplicationErrorMessageCode.SNSTYPE_IS_MISMATCHED)
        }
}

data class TokenRes(
    val JWT: String,
    val snsId: String,
    var certCount: Int? = null,
)
