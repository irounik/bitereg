package com.project.bitereg.auth.firebaseimpl

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.bitereg.auth.Authenticator
import com.project.bitereg.models.User
import kotlinx.coroutines.tasks.await

class FirebaseAuthDb(context: Context) : Authenticator {

    init {
        FirebaseApp.initializeApp(context)
    }

    override suspend fun createUser(name: String, email: String, password: String): AuthResponse {
        return try {
            val response = Firebase.auth
                .createUserWithEmailAndPassword(email, password)
                .await()
            if (response.user == null) AuthResponse.Failure(Exception("Something went wrong"))
            AuthResponse.Success(User(response.user!!.uid, name, email))
        } catch (e: Exception) {
            e.printStackTrace()
            AuthResponse.Failure(e)
        }
    }

    override suspend fun loginUser(email: String, password: String): AuthResponse {
        return try {
            val response = Firebase.auth
                .signInWithEmailAndPassword(email, password)
                .await()
            if (response.user == null) {
                return AuthResponse.Failure(Exception("Something went wrong"))
            }
            AuthResponse.Success(User(id = response.user!!.uid))
        } catch (e: Exception) {
            e.printStackTrace()
            AuthResponse.Failure(e)
        }
    }

    override fun isUserLoggedIn(): Boolean {
        return Firebase.auth.currentUser != null
    }
}