package com.project.bitereg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.bitereg.auth.Authenticator
import com.project.bitereg.auth.firebaseimpl.AuthResponse
import com.project.bitereg.db.UserDao
import com.project.bitereg.models.UserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authDb: Authenticator,
    private val userDao: UserDao
) : ViewModel() {

    val authResultFlow = MutableStateFlow<AuthResponse>(AuthResponse.None)

    fun createUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            authResultFlow.emit(AuthResponse.Loading)
            val response = authDb.createUser(name, email, password)
            if (response is AuthResponse.Success) {
                userDao.addUser(response.authResult)
            }
            authResultFlow.emit(response)
        }
    }

    suspend fun loginUser(email: String, password: String) {
        val response = authDb.loginUser(email, password)
        authResultFlow.emit(response)
    }

    suspend fun logoutUser(): Boolean {
        authDb.logoutUser()
        return true
    }

    suspend fun updateUserDetails(userDetails: UserDetails): Result<Boolean> {
        return authDb.getCurrentUser()?.let {
            it.details = userDetails
            userDao.updateUserDetails(it)
        } ?: Result.failure(Exception("User not found!"))
    }

}