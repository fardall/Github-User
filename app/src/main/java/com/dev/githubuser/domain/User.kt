package com.dev.githubuser.domain

data class User (
    val followers: Int? = null,
    val avatarUrl: String? = null,
    val following: Int? = null,
    val name: String? = null,
    val company: String? = null,
    val location: String? = null,
    val publicRepos: Int? = null,
    val login: String? = null
)