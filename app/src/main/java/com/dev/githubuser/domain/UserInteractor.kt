package com.dev.githubuser.domain

import io.reactivex.Flowable

class UserInteractor(private val userRepository: IUserRepository) : UserUseCase {
    override fun getFavoriteUsers(): Flowable<List<User>> {
        return userRepository.getFavoriteUsers()
    }

    override fun insert(user: User) {
        return userRepository.insert(user)
    }

    override fun delete(user: String) {
        return userRepository.delete(user)
    }

    override fun isExist(username: String): Flowable<Int> {
        return userRepository.isExist(username)
    }

    override fun getFollowers(username: String): Flowable<List<User>> {
        return userRepository.getFollowers(username)
    }

    override fun getFollowing(username: String): Flowable<List<User>> {
        return userRepository.getFollowing(username)
    }

    override fun findUser(username: String): Flowable<List<User>> {
        return userRepository.findUser(username)
    }

    override fun getUser(username: String): Flowable<User> {
        return userRepository.getUser(username)
    }
}