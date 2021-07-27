package com.example.kotlinjwt.common.config.basic

import com.example.kotlinjwt.enum.SnsType
import com.example.kotlinjwt.member.repository.MemberInfoRepository
import mu.KotlinLogging
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

private val log = KotlinLogging.logger {}

@Component
class CustomerReactiveUserDetailsService(
    private val memberInfoRepository: MemberInfoRepository
) : ReactiveUserDetailsService {
    override fun findByUsername(username: String?): Mono<UserDetails> {
        val member = memberInfoRepository.findBySnsIdAndSnsTypeAndActive(username!!, SnsType.EMAIL, true)
            ?: throw BadCredentialsException("Invalid Credentials")
        log.debug { "]-----] CustomerReactiveUserDetailsService::findByUsername.member[-----[ ${member}" }
        val authorities = member.roles.map { memberRole -> SimpleGrantedAuthority(memberRole.role.roleType.name) }
        return Mono.just(User(member.id!!.toString(), member.password!!, authorities))
    }
}