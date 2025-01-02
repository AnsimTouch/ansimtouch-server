package com.ansimtouchserver.domain.auth.dto.request

import com.ansimtouchserver.domain.user.entity.UserType
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

data class AuthSignupRequest (
    val username: String,

    val tel: String,

    val password: String,

    @Enumerated(EnumType.STRING)
    val userType: UserType? = null
)
