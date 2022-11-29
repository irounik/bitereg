package com.project.bitereg.db.firebaseimpl.daos

import com.google.firebase.auth.FirebaseAuth
import com.project.bitereg.db.UserDao
import com.project.bitereg.models.User
import kotlinx.coroutines.tasks.await

class FirebaseUserDao : UserDao, FirebaseBaseDao<User>() {

    override val collectionPath: String get() = USERS_COLLECTION_PATH
    override val entity: Class<User> get() = User::class.java

    override suspend fun addUser(user: User): Result<Boolean> = firebaseCreate(user)

    override suspend fun updateUserDetails(user: User): Result<Boolean> = firebaseUpdate(user)

    override fun getCurrentUserId(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    override suspend fun getCurrentUser(): User? {
        val id = getCurrentUserId() ?: return null
        return firebaseGet(id).getOrNull()
    }

    private companion object {
        private const val USERS_COLLECTION_PATH = "users"
    }

}