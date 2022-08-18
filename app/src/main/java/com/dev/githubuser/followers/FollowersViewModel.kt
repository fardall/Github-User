package com.dev.githubuser.followers

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.dev.githubuser.domain.User
import com.dev.githubuser.domain.UserUseCase

class FollowersViewModel(private val useCase: UserUseCase) : ViewModel() {

    fun getFollowers(username: String): LiveData<List<User>> = LiveDataReactiveStreams.fromPublisher(useCase.getFollowers(username))

}