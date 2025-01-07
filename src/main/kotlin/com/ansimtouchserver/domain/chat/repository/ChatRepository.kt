package com.ansimtouchserver.domain.chat.repository

import com.ansimtouchserver.domain.chat.entity.ChatHistory
import org.springframework.data.mongodb.repository.MongoRepository

interface ChatRepository: MongoRepository<ChatHistory, String> {
    fun findByUserId(userId: String): ChatHistory?
}