package com.ansimtouchserver.global.config

import org.springframework.ai.openai.OpenAiChatClient
import org.springframework.ai.openai.api.OpenAiApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class OpenAiConfig (
    @Value("\${spring.ai.openai.api-key}")
    private val apiKey: String,
) {

    @Bean
    fun openAiApi(): OpenAiApi {
        return OpenAiApi(apiKey)
    }

    @Bean
    fun openAiChatClient(): OpenAiChatClient {
        return OpenAiChatClient(openAiApi())
    }
}