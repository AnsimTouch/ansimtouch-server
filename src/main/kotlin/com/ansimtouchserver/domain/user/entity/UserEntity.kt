package com.ansimtouchserver.domain.user.entity

import jakarta.persistence.*

@Entity
class UserEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var username: String,

    @Column(nullable = false, unique = true)
    var tel: String,

    @Column(nullable = false)
    var password: String,

    @Enumerated(EnumType.STRING)
    var userType: UserType? = null
)