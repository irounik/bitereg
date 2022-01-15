package com.project.bitereg.di

import android.content.Context
import com.project.bitereg.auth.Authenticator
import com.project.bitereg.auth.firebaseimpl.FirebaseAuthDb
import com.project.bitereg.db.UserDao
import com.project.bitereg.db.firebaseimpl.daos.FirebaseUserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun providesAuthDatabase(@ApplicationContext context: Context): Authenticator =
        FirebaseAuthDb(context)

    @Singleton
    @Provides
    fun providesUserDao(): UserDao = FirebaseUserDao()
}
