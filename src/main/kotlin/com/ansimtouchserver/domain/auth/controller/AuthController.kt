package com.ansimtouchserver.domain.auth.controller

import com.ansimtouchserver.domain.auth.dto.request.AuthLoginRequest
import com.ansimtouchserver.domain.auth.dto.request.AuthRefreshRequest
import com.ansimtouchserver.domain.auth.dto.request.AuthSignupRequest
import com.ansimtouchserver.domain.auth.dto.response.AuthTokenResponse
import com.ansimtouchserver.domain.auth.service.AuthService
import com.ansimtouchserver.global.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Auth", description = "인증/인가")
@RestController
@RequestMapping("/auth")
class AuthController (
    private val authService: AuthService
) {
    @Operation(summary = "User Sign-Up", description = "보호자는 Protector, 관리대상자는 Ward")
    @PostMapping("/sign-up")
    fun register(@RequestBody authSignupRequest: AuthSignupRequest): BaseResponse<Unit> {
        return authService.signup(authSignupRequest)
    }

    @Operation(summary = "Login")
    @PostMapping("/login")
    fun login(@RequestBody authLoginRequest: AuthLoginRequest): BaseResponse<AuthTokenResponse> {
        return authService.login(authLoginRequest)
    }

    @Operation(summary = "Token Refresh")
    @PostMapping("/refresh")
    fun refresh(
        @RequestBody authRefreshRequest: AuthRefreshRequest
    ): BaseResponse<AuthTokenResponse> {
        return authService.refresh(authRefreshRequest)
    }
}