package com.ansimtouchserver.domain.user.exception

import com.ansimtouchserver.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

enum class UserErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
) : CustomErrorCode {

    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "유저를 찾을 수 없습니다"),
    USER_ALREADY_EXIST(HttpStatus.CONFLICT, "CONFLICT", "유저가 이미 존재합니다"),
    USER_NOT_MATCH(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "비밀번호가 잘못되었습니다."),
    REQUEST_NOT_FOUND(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "요청을 찾을 수 없습니다"),
    USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "유저 권한이 올바르지 않습니다")
}