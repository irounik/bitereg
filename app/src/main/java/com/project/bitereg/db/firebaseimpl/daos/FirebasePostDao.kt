package com.project.bitereg.db.firebaseimpl.daos

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.project.bitereg.db.PostDao
import com.project.bitereg.models.*
import kotlinx.coroutines.tasks.await

class FirebasePostDao : FirebaseBaseDao<Post>(), PostDao {

    override val collectionPath: String get() = "posts"
    override val entity: Class<Post> get() = Post::class.java

    override suspend fun createPost(post: Post): Result<Boolean> {
        return firebaseCreate(post)
    }

    override suspend fun getPosts(userId: String): Result<List<Post>> {
        return try {
            val list = collectionReference.orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(40)
                .get().await().documents.map { convertToPost(it) }
            Result.success(list.filterNotNull())
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    private fun convertToPost(doc: DocumentSnapshot): Post? {
        return when (doc["type"]) {
            Notice.TYPE -> doc.toObject(Notice::class.java)
            Event.TYPE -> doc.toObject(Event::class.java)
            JobOrIntern.TYPE -> doc.toObject(JobOrIntern::class.java)
            else -> doc.toObject(Quote::class.java)
        }
    }

}