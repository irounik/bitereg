package com.project.bitereg.di

import com.project.bitereg.db.IssueDao
import com.project.bitereg.db.firebaseimpl.daos.FirebaseIssueDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object IssueModule {

    @Provides
    fun providedIssueDao(): IssueDao = FirebaseIssueDao()

}