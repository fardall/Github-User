package com.dev.githubuser.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.githubuser.domain.User
import com.dev.githubuser.domain.UserUseCase

class FollowingViewModel(private val useCase: UserUseCase) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowing(username: String): LiveData<List<User>> {
        return useCase.getFollowing(username)
    }
}