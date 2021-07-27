package com.example.kotlinjwt.member.repository

import com.example.kotlinjwt.enum.SnsType
import com.example.kotlinjwt.member.entity.MemberInfo
import org.springframework.data.jpa.repository.JpaRepository

interface MemberInfoRepository : JpaRepository<MemberInfo, Long> {
    fun findByIdAndActive(id:Long, active: Boolean): MemberInfo?
    fun findBySnsIdAndSnsTypeAndActive(snsId: String, snsType: SnsType, active: Boolean): MemberInfo?
}