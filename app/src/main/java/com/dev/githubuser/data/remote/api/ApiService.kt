package com.dev.githubuser.data.remote.api

import com.dev.githubuser.data.remote.responses.DetailUserResponse
import com.dev.githubuser.data.remote.responses.UserResponse
import com.dev.githubuser.data.remote.responses.UsersResponse
import io.reactivex.Flowable
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
    suspend fun getUser(
        @Path("username") username: String?
    ): DetailUserResponse

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