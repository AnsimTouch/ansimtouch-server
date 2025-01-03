package com.ansimtouchserver.domain.user.repository

import com.ansimtouchserver.domain.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByTel(tel: String): Optional<UserEntity>
    fun existsByTel(tel: String): Boolean
}