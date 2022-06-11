package com.project.bitereg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.bitereg.db.IssueDao
import com.project.bitereg.db.UserDao
import com.project.bitereg.models.Issue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueViewModel @Inject constructor(
    private val issueDao: IssueDao,
    private val userDao: UserDao
) : ViewModel() {

    private val _issueCreationFlow: MutableStateFlow<Result<Boolean>?> = MutableStateFlow(null)
    val issueCreationFlow: StateFlow<Result<Boolean>?> get() = _issueCreationFlow

    fun reportIssue(title: String, description: String) {
        val issue = createIssueDoc(title, description)
        viewModelScope.launch {
            issueDao.createIssue(issue).onSuccess {
                _issueCreationFlow.emit(Result.success(it))
            }.onFailure {
                _issueCreationFlow.emit(Result.failure(Exception("Issue creation failed!")))
            }
        }
    }

    private fun createIssueDoc(title: String, description: String): Issue {
        return Issue(
            title = title,
            description = description,
            createdBy = userDao.getCurrentUserId().toString(),
            createdAt = System.currentTimeMillis()
        )
    }
}