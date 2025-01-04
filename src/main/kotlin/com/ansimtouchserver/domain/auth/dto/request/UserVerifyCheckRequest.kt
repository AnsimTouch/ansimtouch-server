package com.ansimtouchserver.domain.auth.dto.request

data class UserVerifyCheckRequest(
    val tel: String,
    val code: String
)
