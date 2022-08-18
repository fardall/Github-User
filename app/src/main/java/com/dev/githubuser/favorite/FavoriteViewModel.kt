package com.dev.githubuser.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dev.githubuser.domain.User
import com.dev.githubuser.domain.UserUseCase

class FavoriteViewModel(private val useCase: UserUseCase) : ViewModel() {

    fun getFavoriteUsers(): LiveData<List<User>> = useCase.getFavoriteUsers()

}