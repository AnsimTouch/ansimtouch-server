package com.ansimtouchserver.domain.attendance.service

import com.ansimtouchserver.domain.attendance.dto.AttendanceDTO
import com.ansimtouchserver.domain.attendance.entity.Attendance
import com.ansimtouchserver.domain.attendance.repository.AttendanceRepository
import com.ansimtouchserver.domain.user.repository.UserRepository
import com.ansimtouchserver.global.dto.BaseResponse
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AttendanceService (
    private val attendanceRepository: AttendanceRepository,
) {
    fun markAttendance(userId: Long): BaseResponse<Unit> {
        val today = LocalDate.now().toString()
        val attendance = attendanceRepository.findByUserIdAndDate(userId.toString(), today).orElse(
            Attendance(userId = userId.toString(), date = today, checked = true)
        )
        Attendance(userId = userId.toString(), date = today, checked = true)
        attendanceRepository.save(attendance)
        return BaseResponse(
            message = "출석체크 성공"
        )
    }

    fun getAttendanceStatus(userId: Long): AttendanceDTO {
        val today = LocalDate.now().toString()
        val attendance = attendanceRepository.findById(userId.toString()).orElse(
            Attendance(userId = userId.toString(), date = today, checked = false)
        )
        attendanceRepository.save(attendance)
        return AttendanceDTO(
            userId = attendance.userId,
            date = attendance.date,
            checked = attendance.checked
        )
    }

//    @Scheduled(cron = "0 0 20 * * ?") // 매일 오후 8시 실행
//    fun sendAttendanceReminder() {
//
//            val today = java.time.LocalDate.now().toString()
//            val allUsers = userRepository.findAll()
//            allUsers.forEach { user ->
//                val attendance = attendanceRepository.findById("${user.id}:$today").orElse(
//                    Attendance(userId = user.id.toString(), date = today)
//                )
//                if (!attendance.checked) {
//                    sendReminderToUser(user.id)
//                }
//            }
//    }
}