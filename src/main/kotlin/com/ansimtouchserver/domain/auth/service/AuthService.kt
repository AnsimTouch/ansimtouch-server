package com.ansimtouchserver.domain.auth.service

import com.ansimtouchserver.domain.auth.dto.request.AuthLoginRequest
import com.ansimtouchserver.domain.auth.dto.request.AuthRefreshRequest
import com.ansimtouchserver.domain.auth.dto.request.AuthSignupRequest
import com.ansimtouchserver.domain.auth.dto.response.AuthTokenResponse
import com.ansimtouchserver.domain.user.entity.UserEntity
import com.ansimtouchserver.domain.user.exception.UserErrorCode
import com.ansimtouchserver.domain.user.repository.UserRepository
import com.ansimtouchserver.global.dto.BaseResponse
import com.ansimtouchserver.global.exception.CustomException
import com.ansimtouchserver.global.jwt.JwtUtil
import com.ansimtouchserver.global.jwt.exception.JwtErrorCode
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
            userType = authSignupRequest.userType
        )
        userRepository.save(user)

        return BaseResponse(
            message = "회원가입 성공"
        )
    }

    fun login(authLoginRequest: AuthLoginRequest) : BaseResponse<AuthTokenResponse> {
        var user = userRepository.findByTel(authLoginRequest.tel).orElseThrow {
            throw CustomException(UserErrorCode.USER_NOT_FOUND)
        }

        if (!BCryptPasswordEncoder().matches(authLoginRequest.password, user.password))
            throw CustomException(UserErrorCode.USER_NOT_MATCH)

        return BaseResponse(
            message = "로그인 성공",
            data = jwtUtil.generateToken(user)
        )
    }

    fun refresh(authRefreshRequest: AuthRefreshRequest) : BaseResponse<AuthTokenResponse> {
        if (authRefreshRequest.refreshToken.isEmpty())
            throw CustomException(JwtErrorCode.JWT_EMPTY_EXCEPTION)

        val id: Long = jwtUtil.getUserId(authRefreshRequest.refreshToken)
        val user = userRepository.findById(id).orElseThrow {
            throw CustomException(UserErrorCode.USER_NOT_FOUND)
        }

        val cachedToken = redisService.getRefreshToken(id)

        if (cachedToken != authRefreshRequest.refreshToken)
            throw CustomException(JwtErrorCode.JWT_TOKEN_ERROR)

        return BaseResponse(
            message = "리프레시 성공",
            data = jwtUtil.generateToken(user)
        )
    }
}