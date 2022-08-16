package com.dev.githubuser.di

import android.app.Application
import com.dev.githubuser.data.UserRepository
import com.dev.githubuser.data.local.LocalDataSource
import com.dev.githubuser.data.local.db.UserRoomDatabase
import com.dev.githubuser.data.remote.RemoteDataSource
import com.dev.githubuser.data.remote.api.ApiConfig
import com.dev.githubuser.domain.IUserRepository
import com.dev.githubuser.domain.UserInteractor
import com.dev.githubuser.domain.UserUseCase

object Injection {

    private fun provideRepository(application: Application) : IUserRepository {
        val db = UserRoomDatabase.getDatabase(application)

        val localDataSource = LocalDataSource.getInstance(db.userDao())
        val remoteDataSource = RemoteDataSource(ApiConfig.getApiService())

        return UserRepository.getInstance(localDataSource, remoteDataSource)
    }

    fun provideUseCase(application: Application) : UserUseCase {
        val repository = provideRepository(application)
        return UserInteractor(repository)
    }
}