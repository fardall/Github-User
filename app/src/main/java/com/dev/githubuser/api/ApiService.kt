package com.dev.githubuser.api

import com.dev.githubuser.responses.ItemsItem
import com.dev.githubuser.responses.UserResponse
import com.dev.githubuser.responses.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: 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")
    @GET("search/users")
    fun findUsers(
        @Query("q") q: String
    ): Call<UsersResponse>

    @Headers("Authorization: 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")
    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String?
    ): Call<UserResponse>

    @Headers("Authorization: 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")
    @GET("/users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @Headers("Authorization: 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")
    @GET("/users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

}