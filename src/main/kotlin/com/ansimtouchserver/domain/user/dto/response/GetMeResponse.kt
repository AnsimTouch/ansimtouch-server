package com.ansimtouchserver.domain.user.dto.response

import com.ansimtouchserver.domain.user.entity.UserType

data class GetMeResponse (
    val id: Long = 0,

    val username: String,

    val tel: String,

    val userType: UserType? = null,

    val fcmToken: String? = null,
)