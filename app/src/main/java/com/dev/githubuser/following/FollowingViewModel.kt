package com.dev.githubuser.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.dev.githubuser.domain.User
import com.dev.githubuser.domain.UserUseCase

class FollowingViewModel(private val useCase: UserUseCase) : ViewModel() {

    fun getFollowing(username: String): LiveData<List<User>> = LiveDataReactiveStreams.fromPublisher(useCase.getFollowing(username))
}