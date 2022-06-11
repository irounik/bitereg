package com.project.bitereg.db

import com.project.bitereg.models.Issue

interface IssueDao {

    suspend fun createIssue(issue: Issue): Result<Boolean>

    suspend fun getIssues(userId: String): Result<List<Issue?>>

}