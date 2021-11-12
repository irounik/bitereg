package com.project.bitereg.auth.firebaseimpl

import com.google.firebase.auth.AuthResult

sealed class AuthResponse {
    class Success(val authResult: AuthResult) : AuthResponse()
    class Failure(val exception: Exception) : AuthResponse()
    object Loading : AuthResponse()
    object None : AuthResponse()
}