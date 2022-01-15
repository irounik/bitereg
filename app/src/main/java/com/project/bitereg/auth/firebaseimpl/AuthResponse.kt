package com.project.bitereg.auth.firebaseimpl

import com.project.bitereg.models.User

sealed class AuthResponse {
    class Success(val authResult: User) : AuthResponse()
    class Failure(val exception: Exception) : AuthResponse()
    object Loading : AuthResponse()
    object None : AuthResponse()
}