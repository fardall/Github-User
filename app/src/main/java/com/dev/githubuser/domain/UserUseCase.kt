package com.dev.githubuser.domain

import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun getFavoriteUsers(): Flowable<List<User>>

    fun insert(user: User)

    fun delete(user: String)

    fun isExist(username: String): Flowable<Int>

    fun getFollowers(username: String): Flowable<List<User>>

    fun getFollowing(username: String): Flowable<List<User>>

    fun findUser(username: String): Flowable<List<User>>

    fun getUser(username: String): Flow<User>
}