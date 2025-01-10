package com.ansimtouchserver.domain.attendance.service

import com.google.firebase.messaging.Message
import org.springframework.stereotype.Service
import com.google.firebase.messaging.FirebaseMessaging

@Service
class NotificationService {
    fun sendNotification(title: String, body: String, token: String) {
        val message = Message.builder()
            .setToken(token)
            .putData("title", title)
            .putData("body", body)
            .build()
        FirebaseMessaging.getInstance().send(message)
    }
}