package com.example.kotlinjwt.member

import com.example.kotlinjwt.common.config.entity.BaseEntity
import com.example.kotlinjwt.member.entity.MemberInfo
import javax.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["member_info_id", "role_id"])])
class MemberRole (
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_info_id")
    var memberInfo: MemberInfo,

    @ManyToOne
    @JoinColumn(name = "role_id")
    var role: Role

): BaseEntity()