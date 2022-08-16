package com.dev.githubuser.followers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.githubuser.data.remote.responses.UserResponse
import com.dev.githubuser.domain.UserUseCase

class FollowersViewModel(private val useCase: UserUseCase) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
//
//    private val _listUser = MutableLiveData<List<UserResponse>>()
//    val listUser: MutableLiveData<List<UserResponse>> = _listUser

    fun getFollowers(username: String): LiveData<List<UserResponse>> = useCase.getFollowers(username)

}