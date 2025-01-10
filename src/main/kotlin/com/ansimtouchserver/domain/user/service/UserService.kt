package com.ansimtouchserver.domain.user.service

import com.ansimtouchserver.domain.user.dto.response.GetMeResponse
import com.ansimtouchserver.domain.user.dto.response.UserRelationResponse
import com.ansimtouchserver.domain.user.entity.Request
import com.ansimtouchserver.domain.user.entity.UserEntity
import com.ansimtouchserver.domain.user.exception.UserErrorCode
import com.ansimtouchserver.domain.user.repository.RequestRepository
import com.ansimtouchserver.domain.user.repository.UserRepository
import com.ansimtouchserver.global.dto.BaseResponse
import com.ansimtouchserver.global.exception.CustomException
import org.springframework.stereotype.Service
import java.security.Principal
import java.time.LocalDateTime

@Service
class UserService (
    private val userRepository: UserRepository,
    private val requestRepository: RequestRepository
) {
    fun formUser(user: UserEntity): GetMeResponse{
        return GetMeResponse(
            id = user.id,
            username = user.username,
            tel = user.tel,
            userType = user.userType,
            fcmToken = user.fcmToken,
            lastUpdatedAt = user.lastUpdatedAt,
            lastLocationLa = user.lastLocationLa,
            lastLocationLo = user.lastLocationLo
        )
    }

    fun getMe(principal: Principal): GetMeResponse {
        val user = userRepository.findByTel(principal.name).orElseThrow{CustomException(UserErrorCode.USER_NOT_FOUND)}
        return formUser(user)
    }

    fun getUser(userId: Long): GetMeResponse {
        val user = userRepository.findById(userId).orElseThrow{CustomException(UserErrorCode.USER_NOT_FOUND)}
        return formUser(user)
    }

    fun registerFcmToken(principal: Principal, fcmToken: String): BaseResponse<Unit>{
        val user = userRepository.findByTel(principal.name).orElseThrow{CustomException(UserErrorCode.USER_NOT_FOUND)}
        user.fcmToken = fcmToken
        userRepository.save(user)
        return BaseResponse(
            message = "fcmToken 등록 성공",
        )
    }

    fun createRequest(protectorId: String, wardId: String): BaseResponse<Unit> {
        val request = Request(id = "$protectorId:$wardId", protectorId = protectorId, wardId = wardId)
        requestRepository.save(request)
        return BaseResponse(
            message = "유저 추가 요청 성공",
        )
    }

    fun getRequestsForProtege(wardId: String): List<Request> {
        return requestRepository.findAll().filter { it.wardId == wardId }
    }

    fun approveRequest(requestId: String): BaseResponse<Unit> {
        val request = requestRepository.findById(requestId).orElseThrow { CustomException(UserErrorCode.REQUEST_NOT_FOUND) }
        val approvedRequest = request.copy(status = "APPROVED")
        requestRepository.save(approvedRequest)

        val user = userRepository.findById(request.protectorId.toLong()).orElseThrow{CustomException(UserErrorCode.USER_NOT_FOUND)}
        val ward = userRepository.findById(request.wardId.toLong()).orElseThrow {CustomException(UserErrorCode.USER_NOT_FOUND)}

        user.wards.add(ward)
        ward.protectors.add(user)

        userRepository.save(user)
        userRepository.save(ward)

        return BaseResponse(
            message = "유저 추가 승인 성공",
        )
    }

    fun getUserRelations(userId: Long): Map<String, List<UserRelationResponse>> {
        val user = userRepository.findById(userId).orElseThrow { CustomException(UserErrorCode.USER_NOT_FOUND)}
        val protectees = user.wards.map { UserRelationResponse(it.id) }
        val protectors = user.protectors.map { UserRelationResponse(it.id) }
        return mapOf("protectees" to protectees, "protectors" to protectors)
    }

    fun updateUserStatus(user: UserEntity, last: LocalDateTime, la: Double, lo: Double): BaseResponse<Unit> {
        user.lastUpdatedAt = last
        user.lastLocationLa = la
        user.lastLocationLa = lo
        userRepository.save(user)
        return BaseResponse(
            message = "유저 상태 업데이트 성공"
        )
    }
}