package com.dev.githubuser.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.githubuser.domain.User
import com.dev.githubuser.domain.UserUseCase

class MainViewModel(private val useCase: UserUseCase) : ViewModel() {
    val query = MutableLiveData<String>()

    // cari user
    fun findUser(username: String): LiveData<List<User>> = LiveDataReactiveStreams.fromPublisher(useCase.findUser(username))

}