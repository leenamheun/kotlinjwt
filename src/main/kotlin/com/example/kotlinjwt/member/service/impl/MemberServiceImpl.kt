package com.example.kotlinjwt.member.service.impl

import com.example.kotlinjwt.member.SigninSnsReq
import com.example.kotlinjwt.member.SignupRes
import com.example.kotlinjwt.member.entity.MemberInfo
import com.example.kotlinjwt.member.repository.MemberInfoRepository
import com.example.kotlinjwt.member.service.MemberService
import mu.KotlinLogging
import org.springframework.stereotype.Service
import javax.transaction.Transactional

private val log = KotlinLogging.logger {}

@Service
class MemberServiceImpl(
    private val memberInfoRepository: MemberInfoRepository,
) : MemberService {

    @Transactional
    override fun signinSns(req: SigninSnsReq): SignupRes {
        val newMember = MemberInfo(
            snsId = req.snsId,
            snsType = req.snsType,
            snsToken = req.snsToken,
            nickName = "connie"
        )
        //db조회
        return memberInfoRepository.findBySnsIdAndSnsTypeAndActive(req.snsId, req.snsType, true)
            ?.let { SignupRes(it.id!!, it.agreeMarketing) }
            ?: SignupRes(createMember(newMember).id!!, false)
    }

    private fun createMember(newMember: MemberInfo): MemberInfo {
        return memberInfoRepository.save(newMember)
            .also {
                log.debug { "]-----] MemberServiceImpl::createMember newMember[-----[ $it" }
            }

    }
}