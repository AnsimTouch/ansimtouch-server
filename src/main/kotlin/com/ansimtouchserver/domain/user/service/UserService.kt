package com.ansimtouchserver.domain.user.service

import com.ansimtouchserver.domain.user.dto.response.GetMeResponse
import com.ansimtouchserver.domain.user.entity.UserEntity
import com.ansimtouchserver.domain.user.exception.UserErrorCode
import com.ansimtouchserver.domain.user.repository.UserRepository
import com.ansimtouchserver.global.exception.CustomException
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class UserService (
    private val userRepository: UserRepository
) {
    fun formUser(user: UserEntity): GetMeResponse{
        return GetMeResponse(
            id = user.id,
            username = user.username,
            tel = user.tel,
            userType = user.userType,
        )
    }

    fun getMe(principal: Principal): GetMeResponse {
        val user = userRepository.findByTel(principal.name).orElseThrow{CustomException(UserErrorCode.USER_NOT_FOUND)}
        return formUser(user)
    }
}