package com.project.bitereg.db.firebaseimpl.daos

import com.project.bitereg.db.IssueDao
import com.project.bitereg.models.Issue
import kotlinx.coroutines.tasks.await

class FirebaseIssueDao : FirebaseBaseDao<Issue>(), IssueDao {

    override val collectionPath: String get() = "issues"
    override val entity: Class<Issue> get() = Issue::class.java

    override suspend fun createIssue(issue: Issue): Result<Boolean> = firebaseCreate(issue)

    override suspend fun getIssues(userId: String): Result<List<Issue?>> {
        return try {
            val issues = collectionReference.whereEqualTo(
                "createdBy", userId
            ).get().await().documents.map {
                it.toObject(Issue::class.java)
            }
            Result.success(issues)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}
