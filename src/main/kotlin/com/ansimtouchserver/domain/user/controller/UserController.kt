package com.ansimtouchserver.domain.user.controller

import com.ansimtouchserver.domain.user.dto.response.GetMeResponse
import com.ansimtouchserver.domain.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/user")
class UserController (
    private val userService: UserService
) {
    @GetMapping("/me")
    fun getMe(principal: Principal): GetMeResponse {
        return userService.getMe(principal)
    }
}