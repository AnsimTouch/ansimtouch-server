package com.ansimtouchserver.domain.attendance.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("Attendance")
data class Attendance(
    @Id val userId: String,
    val date: String,
    var checked: Boolean = false
)
