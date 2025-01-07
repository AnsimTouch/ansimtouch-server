package com.ansimtouchserver.domain.chat.controller

import com.ansimtouchserver.domain.chat.dto.MessageRequest
import com.ansimtouchserver.domain.chat.entity.ChatMessage
import com.ansimtouchserver.domain.chat.service.ChatService
import com.ansimtouchserver.domain.user.entity.UserEntity
import com.ansimtouchserver.domain.user.exception.UserErrorCode
import com.ansimtouchserver.domain.user.repository.UserRepository
import com.ansimtouchserver.global.exception.CustomException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.security.Principal

@Tag(name = "Chat", description = "챗봇")
@RestController
@RequestMapping("/chat")
class ChatController (
    private val chatService: ChatService,
    private val userRepository: UserRepository
) {
    @Operation(summary = "채팅 보내기")
    @PostMapping("/send")
    fun sendMessage(principal: Principal, @RequestBody request: MessageRequest): ChatMessage {
        val user: UserEntity = userRepository.findByTel(principal.name).orElseThrow{CustomException(UserErrorCode.USER_NOT_FOUND)}
        return chatService.sendMessage(user.id.toString(), request.message)
    }

    @Operation(summary = "채팅 초기화")
    @DeleteMapping("/clear")
    fun clearHistory(principal: Principal) {
        val user: UserEntity = userRepository.findByTel(principal.name).orElseThrow{CustomException(UserErrorCode.USER_NOT_FOUND)}
        chatService.clearHistory(user.id.toString())
    }

    @Operation(summary = "채팅기록 조회하기")
    @GetMapping("/history")
    fun getHistory(principal: Principal): List<ChatMessage> {
        val user: UserEntity = userRepository.findByTel(principal.name).orElseThrow{CustomException(UserErrorCode.USER_NOT_FOUND)}
        return chatService.getHistory(user.id.toString())
    }
}