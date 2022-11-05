package com.project.bitereg.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.bitereg.db.IssueDao
import com.project.bitereg.db.PostDao
import com.project.bitereg.db.UserDao
import com.project.bitereg.models.Issue
import com.project.bitereg.models.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postDao: PostDao,
    private val userDao: UserDao
) : ViewModel() {

    private val _posts: MutableLiveData<List<Post>> = MutableLiveData()
    val posts: LiveData<List<Post>> get() = _posts

    fun fetchPosts() {
        viewModelScope.launch {
            userDao.getCurrentUserId()?.let { userId ->
                postDao.getPosts(userId).onSuccess { posts ->
                    _posts.postValue(posts)
                }.onFailure {
                    it.printStackTrace()
                }
            }
        }
    }

}