package com.ansimtouchserver.domain.auth.controller

import com.ansimtouchserver.domain.auth.dto.request.AuthSignupRequest
import com.ansimtouchserver.domain.auth.service.AuthService
import com.ansimtouchserver.global.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth", description = "인증/인가")
@RestController
@RequestMapping("/auth")
class AuthController (
    private val authService: AuthService
) {
    @Operation(summary = "User Sign-Up")
    @PostMapping("/sign-up")
    fun register(@RequestBody authSignupRequest: AuthSignupRequest): BaseResponse<Unit> {
        return authService.signup(authSignupRequest)
    }
}