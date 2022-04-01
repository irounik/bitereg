package com.project.bitereg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.bitereg.auth.Authenticator
import com.project.bitereg.auth.firebaseimpl.AuthResponse
import com.project.bitereg.db.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authDb: Authenticator,
    private val userDao: UserDao
) : ViewModel() {

    companion object {
        const val TAG = "AuthViewModel"
    }

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
        authDb.loginUser(email, password)
    }

    suspend fun updateUserDetails(userDetails: UserDetails): Boolean {
        val currentUser = authDb.getCurrentUser() ?: return false
        currentUser.details = userDetails
        return userDao.updateUserDetails(currentUser)
    }

}