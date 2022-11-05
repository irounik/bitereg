package com.project.bitereg.db

import com.project.bitereg.models.Post

interface PostDao {

    suspend fun createPost(post: Post): Result<Boolean>

    suspend fun getPosts(userId: String): Result<List<Post>>

}