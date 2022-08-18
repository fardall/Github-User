package com.dev.githubuser.domain

import androidx.lifecycle.LiveData

class UserInteractor(private val userRepository: IUserRepository) : UserUseCase {
    override fun getFavoriteUsers(): LiveData<List<User>> {
        return userRepository.getFavoriteUsers()
    }

    override fun insert(user: User) {
        return userRepository.insert(user)
    }

    override fun delete(user: String) {
        return userRepository.delete(user)
    }

    override fun isExist(username: String): LiveData<Int> {
        return userRepository.isExist(username)
    }

    override fun getFollowers(username: String): LiveData<List<User>> {
        return userRepository.getFollowers(username)
    }

    override fun getFollowing(username: String): LiveData<List<User>> {
        return userRepository.getFollowing(username)
    }

    override fun findUser(username: String): LiveData<List<User>> {
        return userRepository.findUser(username)
    }

    override fun getUser(username: String): LiveData<User> {
        return userRepository.getUser(username)
    }
}