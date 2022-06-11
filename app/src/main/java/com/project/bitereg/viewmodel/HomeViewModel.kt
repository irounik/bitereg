package com.project.bitereg.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.bitereg.db.IssueDao
import com.project.bitereg.db.UserDao
import com.project.bitereg.models.Issue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val issueDao: IssueDao,
    private val userDao: UserDao
) : ViewModel() {

    private val _issues: MutableLiveData<List<Issue>> = MutableLiveData()
    val issues: LiveData<List<Issue>> get() = _issues

    fun fetchIssues() {
        viewModelScope.launch {
            userDao.getCurrentUserId()?.let { userId ->
                issueDao.getIssues(userId).onSuccess { issues ->
                    _issues.postValue(issues.filterNotNull())
                }.onFailure {
                    it.printStackTrace()
                }
            }
        }
    }

}