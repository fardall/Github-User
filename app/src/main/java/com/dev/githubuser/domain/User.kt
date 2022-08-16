package com.dev.githubuser.domain

data class User (
    val followers: Int,
    val avatarUrl: String,
    val following: Int,
    val name: String,
    val company: String,
    val location: String,
    val publicRepos: Int,
    val login: String
)