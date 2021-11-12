package com.project.bitereg.auth

import com.project.bitereg.auth.firebaseimpl.AuthResponse

interface Authenticator {
    suspend fun createUser(email: String, password: String): AuthResponse
    suspend fun loginUser(email: String, password: String): AuthResponse
}