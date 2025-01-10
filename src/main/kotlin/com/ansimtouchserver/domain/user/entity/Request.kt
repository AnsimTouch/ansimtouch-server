package com.ansimtouchserver.domain.user.entity

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("Request")
data class Request(
    @Id val id: String,
    val protectorId: String,
    val wardId: String,
    val status: String = "PENDING"
)
