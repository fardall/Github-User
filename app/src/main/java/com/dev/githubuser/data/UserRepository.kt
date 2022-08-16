package com.dev.githubuser.data

import androidx.lifecycle.LiveData
import com.dev.githubuser.data.local.LocalDataSource
import com.dev.githubuser.data.local.db.UserEntity
import com.dev.githubuser.data.remote.RemoteDataSource
import com.dev.githubuser.data.remote.responses.UserResponse
import com.dev.githubuser.domain.IUserRepository

class UserRepository private constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IUserRepository {

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
                        localDataSource: LocalDataSource,
                        remoteDataSource: RemoteDataSource): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(localDataSource, remoteDataSource)
            }
    }

    override fun getFavoriteUsers(): LiveData<List<UserEntity>> = localDataSource.getFavoriteUsers()

    override fun insert(userEntity: UserEntity) {
        localDataSource.insert(userEntity)
    }

    override fun delete(user: String) {
        localDataSource.delete(user)
    }

    override fun isExist(username: String): LiveData<Int> = localDataSource.isExist(username)

    override fun getFollowers(username: String): LiveData<List<UserResponse>> {
        return remoteDataSource.getFollowers(username)

    }

    override fun getFollowing(username: String): LiveData<List<UserResponse>> {
        return remoteDataSource.getFollowing(username)
    }
}