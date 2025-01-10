package com.ansimtouchserver.domain.user.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Table(name = "users")
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
    var userType: UserType? = null,

    var fcmToken: String? = null,

    @ManyToMany
    @JoinTable(
        name = "protector_protectee",
        joinColumns = [JoinColumn(name = "protector_id")],
        inverseJoinColumns = [JoinColumn(name = "wards_id")]
    )
    val wards: MutableSet<UserEntity> = mutableSetOf(),

    @ManyToMany(mappedBy = "wards")
    val protectors: MutableSet<UserEntity> = mutableSetOf(),

    var lastLocationLa: Double? = null,
    var lastLocationLo: Double? = null,
    var lastUpdatedAt: LocalDateTime = LocalDateTime.now()
)