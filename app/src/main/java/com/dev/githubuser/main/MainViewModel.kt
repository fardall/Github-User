package com.dev.githubuser.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.githubuser.api.ApiConfig
import com.dev.githubuser.responses.ItemsItem
import com.dev.githubuser.responses.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    // cari user
    fun findUser(username: String = "Fardal") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().findUsers(username)
        client.enqueue(object : Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}" )
            }

        })
    }

    companion object {
        private const val TAG = "asd"
    }
}