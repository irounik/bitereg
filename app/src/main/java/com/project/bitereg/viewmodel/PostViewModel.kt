package com.project.bitereg.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.bitereg.db.ImageDao
import com.project.bitereg.db.PostDao
import com.project.bitereg.db.UserDao
import com.project.bitereg.models.Event
import com.project.bitereg.models.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postDao: PostDao,
    private val userDao: UserDao,
    private val imageDao: ImageDao
) : ViewModel() {

    private val _postCreationFlow: MutableStateFlow<Result<Boolean>?> = MutableStateFlow(null)
    val postCreationFlow: StateFlow<Result<Boolean>?> get() = _postCreationFlow

    private val _postBitmap: MutableLiveData<Uri> = MutableLiveData()
    val postBitmap: LiveData<Uri> = _postBitmap

    private var bitmapUri: Uri? = null

    fun isPosterSelected(): Boolean {
        return bitmapUri != null
    }

    fun createPost(post: Post) {
        viewModelScope.launch {
            userDao.getCurrentUser()?.let {
                post.setCreationDetails(it.id.toString(), it.profilePicUrl, it.name)
            }

            postDao.createPost(post).onSuccess {
                _postCreationFlow.emit(Result.success(it))
            }.onFailure {
                _postCreationFlow.emit(Result.failure(Exception("post creation failed!")))
            }
        }
    }

    fun updateBitmap(imageUri: Uri) {
        bitmapUri = imageUri
        _postBitmap.postValue(
            imageUri
        )
    }

    fun publishEvent(registrationUrl: String) {
        if (bitmapUri == null) return
        viewModelScope.launch {
            val uploadEvent = uploadImage(bitmapUri!!)
            if (uploadEvent.isSuccess) {
                createPost(
                    Event(
                        "${uploadEvent.getOrNull()}",
                        registrationUrl
                    )
                )
            }
        }
    }

    private suspend fun uploadImage(imageUri: Uri): Result<String> {
        return imageDao.uploadImage(imageUri)
    }
}