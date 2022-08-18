package com.dev.githubuser.util

import com.dev.githubuser.data.local.db.UserEntity
import com.dev.githubuser.data.remote.responses.DetailUserResponse
import com.dev.githubuser.data.remote.responses.UserResponse
import com.dev.githubuser.domain.User

object DataMapper {
    fun mapResponsesToDomain(input: List<UserResponse>): List<User> {
        val userList = ArrayList<User>()
        input.map {
            val user = User(
                login = it.login,
                avatarUrl = it.avatarUrl
            )
            userList.add(user)
        }
        return userList
    }

    fun mapEntitiesToDomain(input: List<UserEntity>): List<User> {
        val userList = ArrayList<User>()
        input.map {
            val user = User(
                name = it.name,
                login = it.username,
                avatarUrl = it.avatar
            )
            userList.add(user)
        }
        return userList
    }

    fun mapResponseToDomain(input: DetailUserResponse): User {
        return User(
            name = input.name,
            avatarUrl = input.avatarUrl,
            company = input.company,
            login = input.login,
            followers = input.followers,
            following = input.following,
            location = input.location,
            publicRepos = input.publicRepos
        )
    }

    fun mapDomainToEntity(input: User): UserEntity {
        return UserEntity(
            name = input.name,
            username = input.login,
            avatar = input.avatarUrl
        )
    }
}