package com.dev.githubuser.domain

import androidx.lifecycle.LiveData
import com.dev.githubuser.data.local.db.UserEntity
import com.dev.githubuser.data.remote.responses.UserResponse

interface IUserRepository {
    fun getFavoriteUsers(): LiveData<List<UserEntity>>

    fun insert(userEntity: UserEntity)

    fun delete(user: String)

    fun isExist(username: String): LiveData<Int>

    fun getFollowers(username: String): LiveData<List<UserResponse>>

    fun getFollowing(username: String): LiveData<List<UserResponse>>
}
