package com.ansimtouchserver.domain.auth.controller

import com.ansimtouchserver.domain.auth.dto.request.UserVerifyCheckRequest
import com.ansimtouchserver.domain.auth.dto.request.UserVerifyCodeRequest
import com.ansimtouchserver.domain.auth.service.VerifyService
import com.ansimtouchserver.global.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Verify", description = "휴대폰 문자인증")
@RestController
@RequestMapping("/verify")
class VerifyController (
    private val verifyService: VerifyService,
) {
    @Operation(summary = "인증코드 문자로 전송", description = "5분 시간제한")
    @PostMapping("/send")
    fun getUserAuthenticateCode(@RequestBody request: UserVerifyCodeRequest): BaseResponse<Unit> {
        return verifyService.sendVerification(request.tel)
    }

    @Operation(summary = "인증코드 확인")
    @PostMapping("/check")
    fun verifyUserAuthenticateCode(@RequestBody request: UserVerifyCheckRequest): BaseResponse<Unit> {
        return verifyService.checkVerification(request)
    }
}