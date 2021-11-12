package com.project.bitereg.auth.firebaseimpl

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.bitereg.auth.Authenticator
import kotlinx.coroutines.tasks.await

class FirebaseAuthDb(context: Context) : Authenticator {

    init {
        FirebaseApp.initializeApp(context)
    }

    override suspend fun createUser(email: String, password: String): AuthResponse {
        return try {
            val response = Firebase.auth
                .createUserWithEmailAndPassword(email, password)
                .await()
            AuthResponse.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            AuthResponse.Failure(e)
        }
    }

    override suspend fun loginUser(email: String, password: String): AuthResponse {
        return try {
            val response = Firebase.auth
                .createUserWithEmailAndPassword(email, password)
                .await()
            AuthResponse.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            AuthResponse.Failure(e)
        }
    }
}