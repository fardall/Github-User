package com.dev.githubuser.data.remote.api

import com.dev.githubuser.data.remote.responses.*
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
    ): Call<UsersResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String?
    ): Call<DetailUserResponse>

    @Headers("Authorization: token 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")
    @GET("/users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<UserResponse>>

    @Headers("Authorization: token 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")
    @GET("/users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<UserResponse>>

}