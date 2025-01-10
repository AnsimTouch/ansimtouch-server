package com.ansimtouchserver.global.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream

@Configuration
class FirebaseConfig {
    @Bean
    fun firebaseMessaging(): FirebaseMessaging {
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(FileInputStream("ansimtouch-e73c0-firebase-adminsdk-th6cu-d47e7c4a34.json")))
            .build()
        FirebaseApp.initializeApp(options)
        return FirebaseMessaging.getInstance()
    }
}