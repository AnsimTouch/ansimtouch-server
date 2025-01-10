package com.ansimtouchserver.domain.attendance.repository

import com.ansimtouchserver.domain.attendance.entity.Attendance
import org.springframework.data.repository.CrudRepository
import java.util.*

interface AttendanceRepository: CrudRepository<Attendance, String> {
    fun findByUserIdAndDate(userId: String, date: String): Optional<Attendance>
}