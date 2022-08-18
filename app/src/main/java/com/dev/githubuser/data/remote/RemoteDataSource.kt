package com.dev.githubuser.data.remote

import android.annotation.SuppressLint
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

class RemoteDataSource(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource = instance ?: synchronized(this) {
            instance ?: RemoteDataSource(apiService)
        }
    }

    fun getFollowers(username: String): Flowable<List<UserResponse>> {
        val result = PublishSubject.create<List<UserResponse>>()

        val client = apiService.getFollowers(username)
        client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe { response -> result.onNext(response) }

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getFollowing(username: String): Flowable<List<UserResponse>> {
        val result = PublishSubject.create<List<UserResponse>>()

        val client = apiService.getFollowing(username)
        client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe { response -> result.onNext(response) }

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getUser(username: String): Flowable<DetailUserResponse> {
        val usersResponse = PublishSubject.create<DetailUserResponse>()

        val client = ApiConfig.getApiService().getUser(username)
        client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe { response -> usersResponse.onNext(response)}

        return usersResponse.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun findUser(username: String): Flowable<UsersResponse> {
        val users = PublishSubject.create<UsersResponse>()
        val client = ApiConfig.getApiService().findUsers(username)
        client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe { value -> users.onNext(value)}

        return users.toFlowable(BackpressureStrategy.BUFFER)
    }
}