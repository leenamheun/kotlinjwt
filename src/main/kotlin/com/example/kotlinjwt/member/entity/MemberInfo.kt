package com.example.kotlinjwt.member.entity

import com.example.kotlinjwt.common.config.entity.BaseEntity
import com.example.kotlinjwt.enum.SnsType
import com.example.kotlinjwt.member.MemberRole
import javax.persistence.*


@Entity
class MemberInfo(
    @Column(length = 200)
    var snsId: String,
    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    var snsType: SnsType,

    var password: String? = null,
    @Lob
    var snsToken: String? = null,
    @Column(length = 200)
    var nickName: String? = null,
    @Column(length = 100)
    var realName: String? = null,
    @Column(length = 50)
    var phoneNumber: String? = null,

    var agreeService: Boolean = false,
    var agreeSecurity: Boolean = false,
    var agreeMarketing: Boolean = false,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "memberInfo")
    var roles: MutableSet<MemberRole> = mutableSetOf()

) : BaseEntity() {
    internal fun addRole(memberRole: MemberRole) {
        roles.add(memberRole)
        memberRole.memberInfo = this;
    }
}