package com.project.bitereg.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.bitereg.auth.Authenticator
import com.project.bitereg.auth.firebaseimpl.AuthResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authDb: Authenticator) : ViewModel() {

    companion object {
        const val TAG = "AuthViewModel"
    }

    val authResultFlow = MutableStateFlow<AuthResponse>(AuthResponse.None)

    fun createUser(email: String, password: String) {
        Log.d(TAG, "createUser: isme aaya 0")
        viewModelScope.launch {
            authResultFlow.emit(AuthResponse.Loading)
            authResultFlow.emit(authDb.createUser(email, password))
        }
    }

    suspend fun loginUser(email: String, password: String) =
        authDb.loginUser(email, password)

}