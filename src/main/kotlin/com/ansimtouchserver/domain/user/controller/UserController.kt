package com.ansimtouchserver.domain.user.controller

import com.ansimtouchserver.domain.user.dto.response.GetMeResponse
import com.ansimtouchserver.domain.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "User", description = "유저 관련 api")
@RestController
@RequestMapping("/user")
class UserController (
    private val userService: UserService
) {
    @Operation(summary = "마이페이지")
    @GetMapping("/me")
    fun getMe(principal: Principal): GetMeResponse {
        return userService.getMe(principal)
    }
}