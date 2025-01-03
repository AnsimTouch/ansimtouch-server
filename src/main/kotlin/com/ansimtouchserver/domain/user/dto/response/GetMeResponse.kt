package com.ansimtouchserver.domain.user.dto.response

import com.ansimtouchserver.domain.user.entity.UserType

data class GetMeResponse (
    val id: Long = 0,

    var username: String,

    var tel: String,

    var userType: UserType? = null
)