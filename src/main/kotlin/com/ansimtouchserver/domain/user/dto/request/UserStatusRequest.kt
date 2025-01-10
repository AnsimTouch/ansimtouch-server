package com.ansimtouchserver.domain.user.dto.request

import java.time.LocalDateTime

data class UserStatusRequest(
    var lastUpdatedAt: LocalDateTime,
    var lastLocationLa: Double,
    var lastLocationLo: Double,
)
