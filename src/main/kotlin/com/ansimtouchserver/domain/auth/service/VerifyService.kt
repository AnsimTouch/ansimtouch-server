package com.ansimtouchserver.domain.auth.service

import com.ansimtouchserver.domain.auth.dto.request.UserVerifyCheckRequest
import com.ansimtouchserver.domain.auth.exception.VerifyErrorCode
import com.ansimtouchserver.global.dto.BaseResponse
import com.ansimtouchserver.global.exception.CustomException
import com.ansimtouchserver.global.redis.RedisService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import net.nurigo.sdk.message.model.Message
import net.nurigo.sdk.message.model.MessageType
import net.nurigo.sdk.message.service.DefaultMessageService
import org.springframework.data.redis.core.StringRedisTemplate
import java.util.concurrent.TimeUnit


@Service
class VerifyService(
    @Value("\${coolsms.api.key}")
    private val apiKey: String,

    @Value("\${coolsms.api.secret}")
    private val apiSecretKey: String,

    @Value("\${coolsms.fromnumber}")
    private val fromNumber: String,

    private val redisTemplate: StringRedisTemplate,
    private val redisService: RedisService
) {
    fun sendVerification(tel: String): BaseResponse<Unit> {
        try {
            val numStr = generateRandomNumber()

            val message = Message(
                kakaoOptions = null,
                naverOptions = null,
                rcsOptions = null,
                type = MessageType.SMS,
                to = tel,
                from = fromNumber,
                text = "인증번호는 [$numStr] 입니다."
            )

            val messageService = DefaultMessageService(apiKey, apiSecretKey, "https://api.coolsms.co.kr")
            messageService.send(message)

            redisTemplate.opsForValue().set(tel, numStr, 5, TimeUnit.MINUTES) // 5분

            return BaseResponse(
                message = "인증코드 전송 성공"
            )
        } catch (e: Exception) {
            throw CustomException(VerifyErrorCode.FAILED_TO_SEND_SMS)
        }
    }

    private fun generateRandomNumber(): String {
        val rand = Random()
        val numStr = StringBuilder()
        for (i in 0..3) {
            numStr.append(rand.nextInt(10))
        }
        return numStr.toString()
    }

    fun checkVerification(request: UserVerifyCheckRequest): BaseResponse<Unit> {
        val storedCode: String? = redisService.getValue(request.tel)

        if(storedCode != null && storedCode == request.code) {
            redisTemplate.delete(request.tel)
            return BaseResponse(
                message = "인증 성공"
            )
        } else {
            throw CustomException(VerifyErrorCode.VERIFICATION_FAILED)
        }
    }
}