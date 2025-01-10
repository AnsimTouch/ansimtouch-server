package com.ansimtouchserver.domain.attendance.dto

data class AttendanceDTO(
    val userId: String,
    val date: String,
    val checked: Boolean
)
