package com.project.bitereg.db

import android.net.Uri

interface ImageDao {

    suspend fun uploadImage(imageUri: Uri): Result<String>

}