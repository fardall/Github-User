package com.dev.githubuser.detail

import androidx.lifecycle.*
import com.dev.githubuser.domain.User
import com.dev.githubuser.domain.UserUseCase

class DetailViewModel(private val useCase: UserUseCase) : ViewModel() {

    fun insert(user: User) {
        useCase.insert(user)
    }

    fun delete(user: String) {
        useCase.delete(user)
    }

    fun isExist(username: String): LiveData<Int> = LiveDataReactiveStreams.fromPublisher(useCase.isExist(username))

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val followers = MutableLiveData<Int>()

    val following = MutableLiveData<Int>()

    val username = MutableLiveData<String>()

    fun getUser(username: String): LiveData<User> = useCase.getUser(username).asLiveData()

}