package com.project.bitereg.auth

import com.project.bitereg.auth.firebaseimpl.AuthResponse

interface Authenticator {
    suspend fun createUser(name: String, email: String, password: String): AuthResponse
    suspend fun loginUser(email: String, password: String): AuthResponse
}