package com.dev.githubuser.data.remote.responses

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("login")
    val login: String
)