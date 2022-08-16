package com.dev.githubuser.domain

import androidx.lifecycle.LiveData
import com.dev.githubuser.data.local.db.UserEntity
import com.dev.githubuser.data.remote.responses.UserResponse

class UserInteractor(private val userRepository: IUserRepository) : UserUseCase {
    override fun getFavoriteUsers(): LiveData<List<UserEntity>> {
        return userRepository.getFavoriteUsers()
    }

    override fun insert(userEntity: UserEntity) {
        return userRepository.insert(userEntity)
    }

    override fun delete(user: String) {
        return userRepository.delete(user)
    }

    override fun isExist(username: String): LiveData<Int> {
        return userRepository.isExist(username)
    }

    override fun getFollowers(username: String): LiveData<List<UserResponse>> {
        return userRepository.getFollowers(username)
    }

    override fun getFollowing(username: String): LiveData<List<UserResponse>> {
        return userRepository.getFollowing(username)
    }
}