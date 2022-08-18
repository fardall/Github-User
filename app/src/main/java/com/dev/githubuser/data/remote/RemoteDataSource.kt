package com.dev.githubuser.data.remote

import android.annotation.SuppressLint
import android.util.Log
import com.dev.githubuser.data.remote.api.ApiConfig
import com.dev.githubuser.data.remote.api.ApiService
import com.dev.githubuser.data.remote.responses.DetailUserResponse
import com.dev.githubuser.data.remote.responses.UserResponse
import com.dev.githubuser.data.remote.responses.UsersResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RemoteDataSource(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource = instance ?: synchronized(this) {
            instance ?: RemoteDataSource(apiService)
        }

        private const val TAG = "asd"
    }

    @SuppressLint("CheckResult")
    fun getFollowers(username: String): Flowable<List<UserResponse>> {
        val result = PublishSubject.create<List<UserResponse>>()

        val client = apiService.getFollowers(username)
        client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe ({ response -> result.onNext(response) }, { e ->
                Log.e(TAG, "getFollowers: ${e.message}")
            })

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getFollowing(username: String): Flowable<List<UserResponse>> {
        val result = PublishSubject.create<List<UserResponse>>()

        val client = apiService.getFollowing(username)
        client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe ({ response -> result.onNext(response) }, { error ->
                Log.e(
                    TAG, "getFollowing: ${error.message}",
                )})

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getUser(username: String): Flow<DetailUserResponse> {
        return flow {
            try {
                val response = ApiConfig.getApiService().getUser(username)
                emit(response)
            } catch (e: Exception) {
                Log.e(TAG, "getUser: ${e.message}")
            }
        }.flowOn(Dispatchers.IO)
    }

    @SuppressLint("CheckResult")
    fun findUser(username: String): Flowable<UsersResponse> {
        val users = PublishSubject.create<UsersResponse>()
        val client = ApiConfig.getApiService().findUsers(username)
        client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe ({ value -> users.onNext(value)}, { e ->
                Log.e(TAG, "findUser: ${e.message}")
            })

        return users.toFlowable(BackpressureStrategy.BUFFER)
    }
}