package com.ansimtouchserver.domain.auth.service

import com.ansimtouchserver.domain.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val userId = username.toLong()
        val user = userRepository.findById(userId).orElseThrow { UsernameNotFoundException("User not found with id: $userId") }
        return org.springframework.security.core.userdetails.User(user.tel, user.password, emptyList())
    }
}