package com.ansimtouchserver.domain.auth.service

import com.ansimtouchserver.domain.auth.dto.request.AuthSignupRequest
import com.ansimtouchserver.domain.user.entity.UserEntity
import com.ansimtouchserver.domain.user.exception.UserErrorCode
import com.ansimtouchserver.domain.user.repository.UserRepository
import com.ansimtouchserver.global.dto.BaseResponse
import com.ansimtouchserver.global.exception.CustomException
import com.ansimtouchserver.global.jwt.JwtUtil
import com.ansimtouchserver.global.redis.RedisService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService (
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil,
    private val redisService: RedisService
) {
    fun signup(authSignupRequest: AuthSignupRequest) : BaseResponse<Unit> {
        if (userRepository.existsByTel(authSignupRequest.tel))
            throw CustomException(UserErrorCode.USER_ALREADY_EXIST)

        val user = UserEntity(
            username = authSignupRequest.username,
            tel = authSignupRequest.tel,
            password = BCryptPasswordEncoder().encode(authSignupRequest.password),
        )
        userRepository.save(user)

        return BaseResponse(
            message = "회원가입 성공"
        )
    }
}