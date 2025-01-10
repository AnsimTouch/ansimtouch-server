package com.ansimtouchserver.global.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.ByteArrayInputStream


@Configuration
class FirebaseConfig (
    @Value("\${firebase.json-key}")
    private val firebaseJsonKey: String
) {
    @Bean
    fun firebaseMessaging(): FirebaseMessaging {
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(ByteArrayInputStream(firebaseJsonKey.toByteArray())))
            .build()
        FirebaseApp.initializeApp(options)
        return FirebaseMessaging.getInstance()
    }
}