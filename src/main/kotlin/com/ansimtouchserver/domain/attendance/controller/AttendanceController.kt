package com.ansimtouchserver.domain.attendance.controller

import com.ansimtouchserver.domain.attendance.dto.AttendanceDTO
import com.ansimtouchserver.domain.attendance.service.AttendanceService
import com.ansimtouchserver.global.dto.BaseResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/attendance")
class AttendanceController (
    private val attendanceService: AttendanceService,
) {
    @PostMapping("/mark/{userId}")
    fun markAttendance(@PathVariable userId: Long): BaseResponse<Unit> {
        return attendanceService.markAttendance(userId)
    }

    @GetMapping("/status/{userId}")
    fun getAttendanceStatus(@PathVariable userId: Long): AttendanceDTO {
        return attendanceService.getAttendanceStatus(userId)
    }
}