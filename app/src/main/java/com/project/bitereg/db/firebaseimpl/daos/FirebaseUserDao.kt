package com.project.bitereg.db.firebaseimpl.daos

import com.google.firebase.firestore.FirebaseFirestore
import com.project.bitereg.db.UserDao
import com.project.bitereg.models.User
import kotlinx.coroutines.tasks.await

class FirebaseUserDao : UserDao {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection(USERS_COLLECTION_PATH)

    override suspend fun addUser(user: User): Boolean {
        return try {
            usersCollection.document(user.id).set(user).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun updateUserDetails(user: User): Boolean {
        return try {
            usersCollection.document(user.id).update(
                mapOf(Pair(USER_DETAILS, user.details))
            ).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private companion object {
        private const val USERS_COLLECTION_PATH = "users"
        private const val USER_DETAILS = "details"
    }

}