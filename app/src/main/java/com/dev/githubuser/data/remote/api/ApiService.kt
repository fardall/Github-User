package com.dev.githubuser.data.remote.api

import com.dev.githubuser.data.remote.responses.DetailUserResponse
import com.dev.githubuser.data.remote.responses.UserResponse
import com.dev.githubuser.data.remote.responses.UsersResponse
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