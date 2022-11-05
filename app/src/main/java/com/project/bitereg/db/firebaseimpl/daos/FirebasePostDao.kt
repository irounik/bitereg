package com.project.bitereg.db.firebaseimpl.daos

import com.project.bitereg.db.PostDao
import com.project.bitereg.models.Post

class FirebasePostDao : FirebaseBaseDao<Post>(), PostDao {

    override val collectionPath: String get() = "posts"
    override val entity: Class<Post> get() = Post::class.java

    override suspend fun createPost(post: Post): Result<Boolean> {
        return firebaseCreate(post)
    }

    override suspend fun getPosts(userId: String): Result<List<Post>> {
        return firebaseGetAll()
    }

}