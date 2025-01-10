package com.ansimtouchserver.global.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.ByteArrayInputStream

@Configuration
class FirebaseConfig {
    @Value("\${firebase.json-key}")
    private lateinit var firebaseJsonKey: String

    @Bean
    fun firebaseMessaging(): FirebaseMessaging {
        try {
            Gson().fromJson(firebaseJsonKey, Map::class.java)
        } catch (e: JsonSyntaxException) {
            log.error(firebaseJsonKey)
            throw IllegalArgumentException("Firebase JSON configuration is malformed", e)
        }

        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(ByteArrayInputStream(firebaseJsonKey.toByteArray())))
            .build()

        FirebaseApp.initializeApp(options)
        return FirebaseMessaging.getInstance()
    }
}