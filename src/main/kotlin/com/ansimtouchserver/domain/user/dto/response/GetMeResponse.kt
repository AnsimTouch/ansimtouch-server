package com.ansimtouchserver.domain.user.dto.response

import com.ansimtouchserver.domain.user.entity.UserType
import java.time.LocalDateTime

data class GetMeResponse (
    val id: Long = 0,

    val username: String,

    val tel: String,

    val userType: UserType? = null,

    val fcmToken: String? = null,

    var lastLocationLa: Double? = null,
    var lastLocationLo: Double? = null,
    var lastUpdatedAt: LocalDateTime? = null,
)