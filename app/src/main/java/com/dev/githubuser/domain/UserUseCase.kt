package com.dev.githubuser.domain

import androidx.lifecycle.LiveData

interface UserUseCase {
    fun getFavoriteUsers(): LiveData<List<User>>

    fun insert(user: User)

    fun delete(user: String)

    fun isExist(username: String): LiveData<Int>

    fun getFollowers(username: String): LiveData<List<User>>

    fun getFollowing(username: String): LiveData<List<User>>

    fun findUser(username: String): LiveData<List<User>>

    fun getUser(username: String): LiveData<User>
}