package com.project.bitereg.db.firebaseimpl.daos

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.project.bitereg.db.ImageDao
import kotlinx.coroutines.tasks.await
import java.util.*

class FirebaseImageDao : ImageDao {

    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference

    override suspend fun uploadImage(imageUri: Uri): Result<String> {
        val imageRefId = "images/${UUID.randomUUID()}"
        return try {
            val imgRef = storageRef.child(imageRefId)
            imgRef.putFile(imageUri).await()
            Result.success(imageRefId)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

}