package com.project.bitereg.db.firebaseimpl.daos

import com.google.firebase.firestore.FirebaseFirestore
import com.project.bitereg.db.UserDao
import com.project.bitereg.models.User

class FirebaseUserDao : UserDao {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection(USERS_COLLECTION_PATH)

    override suspend fun addUser(user: User) {
        usersCollection.document(user.id).set(user)
    }

    private companion object {
        private const val USERS_COLLECTION_PATH = "users"
    }

}