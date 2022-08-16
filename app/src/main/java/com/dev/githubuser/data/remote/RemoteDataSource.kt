package com.dev.githubuser.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.githubuser.data.remote.api.ApiService
import com.dev.githubuser.data.remote.responses.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource = instance ?: synchronized(this) {
            instance ?: RemoteDataSource(apiService)
        }
    }

    fun getFollowers(username: String): LiveData<List<UserResponse>> {
        val result = MutableLiveData<List<UserResponse>>()

        val client = apiService.getFollowers(username)
        client.enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    result.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
            }
        })

        return result
    }

    fun getFollowing(username: String): LiveData<List<UserResponse>> {
        val result = MutableLiveData<List<UserResponse>>()

        val client = apiService.getFollowing(username)
        client.enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    result.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {

            }

        })

        return result
    }
}