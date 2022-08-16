package com.dev.githubuser.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.githubuser.data.local.db.UserEntity
import com.dev.githubuser.data.remote.api.ApiConfig
import com.dev.githubuser.data.remote.responses.DetailUserResponse
import com.dev.githubuser.domain.UserUseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val useCase: UserUseCase) : ViewModel() {

    fun insert(userEntity: UserEntity) {
        useCase.insert(userEntity)
    }

    fun delete(user: String) {
        useCase.delete(user)
    }

    fun isExist(username: String): LiveData<Int> = useCase.isExist(username)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _user = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _user

    private val _followers = MutableLiveData<Int>()
    val followers: LiveData<Int> = _followers

    private val _following = MutableLiveData<Int>()
    val following: LiveData<Int> = _following

    val username = MutableLiveData<String>()

    fun getUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(call: Call<DetailUserResponse>, responseDetail: Response<DetailUserResponse>) {
                _isLoading.value = false
                if (responseDetail.isSuccessful) {
                    _user.value = responseDetail.body()
                    _followers.value = responseDetail.body()?.followers
                    _following.value = responseDetail.body()?.following
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.e("asd", "onFailure: ${t.message}")
            }
        })
    }
}