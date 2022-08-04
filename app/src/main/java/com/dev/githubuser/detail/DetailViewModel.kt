package com.dev.githubuser.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.githubuser.api.ApiConfig
import com.dev.githubuser.db.User
import com.dev.githubuser.repository.UserRepository
import com.dev.githubuser.responses.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    private val userRepository: UserRepository = UserRepository(application)

    fun insert(user: User) {
        userRepository.insert(user)
    }

    fun delete(user: String) {
        userRepository.delete(user)
    }

    fun isExist(username: String): LiveData<Int> = userRepository.isExist(username)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse> = _user

    private val _followers = MutableLiveData<Int>()
    val followers: LiveData<Int> = _followers

    private val _following = MutableLiveData<Int>()
    val following: LiveData<Int> = _following

    val username = MutableLiveData<String>()

    fun getUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                    _followers.value = response.body()?.followers
                    _following.value = response.body()?.following
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("asd", "onFailure: ${t.message}")
            }
        })
    }
}