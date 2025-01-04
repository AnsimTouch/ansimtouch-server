package com.ansimtouchserver.domain.auth.exception

import com.ansimtouchserver.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

enum class VerifyErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
) : CustomErrorCode {
    FAILED_TO_SEND_SMS(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "메시지를 전송하는데 실패하였습니다."),
    VERIFICATION_FAILED(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "인증코드를 확인하는데 실패하였습니다."),
}