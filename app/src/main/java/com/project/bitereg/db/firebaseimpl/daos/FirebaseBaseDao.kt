package com.project.bitereg.db.firebaseimpl.daos

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.project.bitereg.models.BaseModel
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.util.UUID

abstract class FirebaseBaseDao<T: BaseModel> {

    abstract val collectionPath: String
    abstract val entity: Class<T>

    val db by lazy {
        FirebaseFirestore.getInstance()
    }

    val collectionReference: CollectionReference by lazy {
        db.collection(collectionPath)
    }

    suspend fun firebaseGet(id: String): Result<T> {
        val doc = collectionReference.document(id).get().await().toObject(entity)
        return doc?.run { Result.success(this) } ?: Result.failure(DocNotFound())
    }

    suspend fun firebaseCreate(entity: T): Result<Boolean> {
        return try {
            if(entity.id == null) entity.id = UUID.randomUUID().toString()
            collectionReference.document(entity.id!!).set(entity).await()
            Result.success(true)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    suspend fun firebaseUpdate(entity: T): Result<Boolean> {
        return try {
            collectionReference.document(entity.id!!).set(entity).await()
            Result.success(true)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

}

class DocNotFound : Exception("Document not found!")
