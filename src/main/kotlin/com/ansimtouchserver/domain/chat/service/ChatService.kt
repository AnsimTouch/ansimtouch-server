package com.ansimtouchserver.domain.chat.service


import com.ansimtouchserver.domain.chat.entity.ChatHistory
import com.ansimtouchserver.domain.chat.entity.ChatMessage
import com.ansimtouchserver.domain.chat.repository.ChatRepository
import org.springframework.ai.chat.messages.AssistantMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.openai.OpenAiChatClient
import org.springframework.stereotype.Service

@Service
class ChatService (
    private val chatClient: OpenAiChatClient,
    private val chatRepository: ChatRepository
) {
    fun sendMessage(userId: String, content: String): ChatMessage {
        val history = chatRepository.findByUserId(userId)
            ?: ChatHistory(userId = userId)

        val userMessage = ChatMessage(content = content, role = "user")
        history.messages.add(userMessage)

        val initialPrompt = """
        너는 독거노인을 위한 AI 챗봇이야. 이름은 안심이야.
        너는 독거노인이 하는 말을 귀담아 듣고 잘 대답해줘야 해.
        무슨 질문을 하면 그에 맞는 올바른 대답을 해야 해.
        노인이 이해하기 쉽도록 간단명료하게 말해야 해.
        반드시 존댓말을 사용할 것.
        """.trimIndent()

        // OpenAI에 보낼 프롬프트 구성
        val prompt = mutableListOf(
            AssistantMessage(initialPrompt) // 초기 지침 추가
        ) + history.messages.map {
            when (it.role) {
                "user" -> UserMessage(it.content) // 유저 메시지
                "assistant" -> AssistantMessage(it.content) // 어시스턴트 메시지
                else -> throw IllegalArgumentException("Unknown role: ${it.role}")
            }
        }

        val response = chatClient.call(prompt.toString())

        val assistantMessage = ChatMessage(
            content = response,
            role = "assistant"
        )
        history.messages.add(assistantMessage)

        chatRepository.save(history)
        return assistantMessage
    }

    fun clearHistory(userId: String) {
        val history = chatRepository.findByUserId(userId)
        history?.messages?.clear()
        history?.let { chatRepository.save(it) }
    }

    fun getHistory(userId: String): List<ChatMessage> {
        return chatRepository.findByUserId(userId)?.messages ?: emptyList()
    }
}