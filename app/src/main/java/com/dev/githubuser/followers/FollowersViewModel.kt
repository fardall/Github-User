package com.dev.githubuser.followers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.githubuser.domain.User
import com.dev.githubuser.domain.UserUseCase

class FollowersViewModel(private val useCase: UserUseCase) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowers(username: String): LiveData<List<User>> = useCase.getFollowers(username)

}