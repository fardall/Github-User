package com.dev.githubuser

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun findUsers(
        @Query("q") q: String
    ): Call<UsersResponse>

    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String
    ): Call<UserResponse>

}