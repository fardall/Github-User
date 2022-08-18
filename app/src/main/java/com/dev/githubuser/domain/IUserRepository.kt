package com.dev.githubuser.domain

import io.reactivex.Flowable

interface IUserRepository {
    fun getFavoriteUsers(): Flowable<List<User>>

    fun insert(user: User)

    fun delete(user: String)

    fun isExist(username: String): Flowable<Int>

    fun getFollowers(username: String): Flowable<List<User>>

    fun getFollowing(username: String): Flowable<List<User>>

    fun findUser(username: String): Flowable<List<User>>

    fun getUser(username: String): Flowable<User>
}
