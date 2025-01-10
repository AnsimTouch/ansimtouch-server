package com.ansimtouchserver.domain.attendance.controller

import com.ansimtouchserver.domain.attendance.dto.AttendanceDTO
import com.ansimtouchserver.domain.attendance.service.AttendanceService
import com.ansimtouchserver.global.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Attendance", description = "출석체크")
@RestController
@RequestMapping("/attendance")
class AttendanceController (
    private val attendanceService: AttendanceService,
) {
    @Operation(summary = "출석체크 하기")
    @PostMapping("/mark/{userId}")
    fun markAttendance(@PathVariable userId: Long): BaseResponse<Unit> {
        return attendanceService.markAttendance(userId)
    }

    @Operation(summary = "출석체크 했는지 조회")
    @GetMapping("/status/{userId}")
    fun getAttendanceStatus(@PathVariable userId: Long): AttendanceDTO {
        return attendanceService.getAttendanceStatus(userId)
    }
}