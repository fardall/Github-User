package com.dev.githubuser.data.remote.api

import com.dev.githubuser.data.remote.responses.*
import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun findUsers(
        @Query("q") q: String
    ): Flowable<UsersResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String?
    ): Flowable<DetailUserResponse>

    @Headers("Authorization: token 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")
    @GET("/users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Flowable<List<UserResponse>>

    @Headers("Authorization: token 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")
    @GET("/users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Flowable<List<UserResponse>>

}