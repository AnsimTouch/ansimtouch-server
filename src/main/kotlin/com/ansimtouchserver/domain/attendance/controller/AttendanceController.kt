package com.ansimtouchserver.domain.attendance.controller

import com.ansimtouchserver.domain.attendance.dto.AttendanceDTO
import com.ansimtouchserver.domain.attendance.service.AttendanceService
import com.ansimtouchserver.domain.user.exception.UserErrorCode
import com.ansimtouchserver.domain.user.repository.UserRepository
import com.ansimtouchserver.global.dto.BaseResponse
import com.ansimtouchserver.global.exception.CustomException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.security.Principal

@Tag(name = "Attendance", description = "출석체크")
@RestController
@RequestMapping("/attendance")
class AttendanceController (
    private val attendanceService: AttendanceService,
    private val userRepository: UserRepository
) {
    @Operation(summary = "출석체크 하기")
    @PostMapping("/mark")
    fun markAttendance(principal: Principal): BaseResponse<Unit> {
        val user = userRepository.findByTel(principal.name).orElseThrow { CustomException(UserErrorCode.USER_NOT_FOUND) }
        return attendanceService.markAttendance(user.id)
    }

    @Operation(summary = "출석체크 했는지 조회")
    @GetMapping("/status")
    fun getAttendanceStatus(@RequestParam userId: Long): AttendanceDTO {
        return attendanceService.getAttendanceStatus(userId)
    }
}