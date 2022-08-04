package com.dev.githubuser.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dev.githubuser.db.User
import com.dev.githubuser.repository.UserRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val userRepository = UserRepository(application)

    fun getFavoriteUsers(): LiveData<List<User>> = userRepository.getFavoriteUsers()

}