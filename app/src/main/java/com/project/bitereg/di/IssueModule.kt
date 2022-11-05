package com.project.bitereg.di

import com.project.bitereg.db.IssueDao
import com.project.bitereg.db.PostDao
import com.project.bitereg.db.firebaseimpl.daos.FirebaseIssueDao
import com.project.bitereg.db.firebaseimpl.daos.FirebasePostDao
import com.project.bitereg.db.mocks.PostDaoMock
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object IssueModule {

    @Provides
    fun providedIssueDao(): IssueDao = FirebaseIssueDao()

    @Provides
    fun providePostDao(): PostDao = FirebasePostDao()
//    fun providePostDao(): PostDao = PostDaoMock()

}