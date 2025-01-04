package com.ansimtouchserver.domain.auth.service

import com.ansimtouchserver.domain.auth.exception.VerifyErrorCode
import com.ansimtouchserver.global.dto.BaseResponse
import com.ansimtouchserver.global.exception.CustomException
import com.ansimtouchserver.global.redis.RedisService
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import net.nurigo.sdk.message.model.Message
import net.nurigo.sdk.message.model.MessageType
import net.nurigo.sdk.message.service.DefaultMessageService


@Slf4j
@Service
class VerifyService(
    @Value("\${coolsms.api.key}")
    private val apiKey: String,

    @Value("\${coolsms.api.secret}")
    private val apiSecretKey: String,

    @Value("\${coolsms.fromnumber}")
    private val fromNumber: String,

    private val redisService: RedisService,
) {
    fun sendVerification(to: String): BaseResponse<Unit> {
        try {
            val numStr = generateRandomNumber()

            val message = Message(
                kakaoOptions = null,
                naverOptions = null,
                rcsOptions = null,
                type = MessageType.SMS, // 메세지 타입
                to = to,
                from = fromNumber,
                text = "인증번호는 [$numStr] 입니다."
            )

            val messageService = DefaultMessageService(apiKey, apiSecretKey, "https://api.coolsms.co.kr")
            messageService.send(message)

            return BaseResponse(
                message = "인증코드 전송 성공"
            )
        } catch (e: Exception) {
            throw CustomException(VerifyErrorCode.FAILED_TO_SEND_SMS)
        }
    }

    private fun generateRandomNumber(): String {
        val rand: Random = Random()
        val numStr = StringBuilder()
        for (i in 0..3) {
            numStr.append(rand.nextInt(10))
        }
        return numStr.toString()
    }

}