package com.ansimtouchserver.domain.user.controller

import com.ansimtouchserver.domain.user.dto.request.FcmRequest
import com.ansimtouchserver.domain.user.dto.response.GetMeResponse
import com.ansimtouchserver.domain.user.entity.Request
import com.ansimtouchserver.domain.user.entity.UserType
import com.ansimtouchserver.domain.user.exception.UserErrorCode
import com.ansimtouchserver.domain.user.repository.UserRepository
import com.ansimtouchserver.domain.user.service.UserService
import com.ansimtouchserver.global.dto.BaseResponse
import com.ansimtouchserver.global.exception.CustomException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "User", description = "유저 관련 api")
@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
    private val userRepository: UserRepository
) {
    @Operation(summary = "마이페이지")
    @GetMapping("/me")
    fun getMe(principal: Principal): GetMeResponse {
        return userService.getMe(principal)
    }

    @Operation(summary = "마이페이지")
    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long): GetMeResponse {
        return userService.getUser(userId)
    }

    @Operation(summary = "fcm 토큰 입력")
    @PostMapping("/fcm")
    fun registerFcmToken(principal: Principal, @RequestBody fcmRequest: FcmRequest): BaseResponse<Unit> {
        return userService.registerFcmToken(principal, fcmRequest.fcmToken)
    }

    @Operation(summary = "피보호자 추가")
    @PostMapping("/ward")
    fun addWard(principal: Principal, @RequestParam wardTel: String): BaseResponse<Unit> {
        val user = userRepository.findByTel(principal.name).orElseThrow {CustomException(UserErrorCode.USER_NOT_FOUND)}
        val ward = userRepository.findByTel(wardTel).orElseThrow{CustomException(UserErrorCode.USER_NOT_FOUND)}

        if (user.userType != UserType.Protector || ward.userType != UserType.Ward)
            CustomException(UserErrorCode.USER_UNAUTHORIZED)
        return userService.createRequest(user.id.toString(), ward.id.toString())
    }

    @Operation(summary = "요청내역 보기")
    @GetMapping("/request")
    fun getRequestsForProtege(principal: Principal): List<Request> {
        val ward = userRepository.findByTel(principal.name).orElseThrow {CustomException(UserErrorCode.USER_NOT_FOUND)}
        if (ward.userType != UserType.Ward)
            CustomException(UserErrorCode.USER_UNAUTHORIZED)
        return userService.getRequestsForProtege(ward.id.toString())
    }

    @Operation(summary = "요청 승인")
    @PostMapping("/approve")
    fun approveRequest(requestId: String): BaseResponse<Unit> {
        return userService.approveRequest(requestId)
    }
}