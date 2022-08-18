package com.dev.githubuser.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.githubuser.data.remote.api.ApiConfig
import com.dev.githubuser.data.remote.api.ApiService
import com.dev.githubuser.data.remote.responses.DetailUserResponse
import com.dev.githubuser.data.remote.responses.UserResponse
import com.dev.githubuser.data.remote.responses.UsersResponse
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

    fun getUser(username: String): LiveData<DetailUserResponse> {
        val usersResponse = MutableLiveData<DetailUserResponse>()

        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(call: Call<DetailUserResponse>, responseDetail: Response<DetailUserResponse>) {
                if (responseDetail.isSuccessful) {
                    usersResponse.value = responseDetail.body()
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.e("asd", "onFailure: ${t.message}")
            }
        })

        return usersResponse
    }

    fun findUser(username: String): LiveData<List<UserResponse>> {
        val users = MutableLiveData<List<UserResponse>>()
        val client = ApiConfig.getApiService().findUsers(username)
        client.enqueue(object : Callback<UsersResponse>{
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if (response.isSuccessful) {
                    users.value = response.body()?.items
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
            }
        })

        return users
    }
}