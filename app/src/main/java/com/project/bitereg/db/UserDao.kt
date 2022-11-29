package com.project.bitereg.db

import com.project.bitereg.models.User

interface UserDao {
    suspend fun addUser(user: User): Result<Boolean>
    suspend fun updateUserDetails(user: User): Result<Boolean>
    suspend fun getCurrentUser(): User?
    fun getCurrentUserId(): String?
}