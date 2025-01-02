package com.ansimtouchserver.domain.auth.dto.request

data class AuthLoginRequest(
    val tel: String,
    val password: String
)
