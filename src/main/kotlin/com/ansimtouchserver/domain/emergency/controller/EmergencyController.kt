package com.ansimtouchserver.domain.emergency.controller

import com.ansimtouchserver.domain.user.exception.UserErrorCode
import com.ansimtouchserver.domain.user.repository.UserRepository
import com.ansimtouchserver.global.dto.BaseResponse
import com.ansimtouchserver.global.exception.CustomException
import com.ansimtouchserver.global.util.NotificationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Emergency", description = "긴급호출 알림 발송")
@RestController
@RequestMapping
class EmergencyController(
    private val notificationService: NotificationService,
    private val userRepository: UserRepository
) {
    @Operation(summary = "긴급호출")
    @PostMapping("/emergency")
    fun sendEmergencyNotification(@RequestParam userId: Long): BaseResponse<Unit> {
        val user = userRepository.findById(userId).orElseThrow{CustomException(UserErrorCode.USER_NOT_FOUND)}
        if (user.fcmToken == null)
            throw CustomException(UserErrorCode.FCM_TOKEN_NOT_FOUND)
        notificationService.sendNotification("긴급알림", "보호자가 상태를 요청합니다.", user.fcmToken!!)
        return BaseResponse(
            message = "긴급알림을 성공적으로 전송하였습니다."
        )
    }
}