package com.dev.githubuser.data

import com.dev.githubuser.data.local.LocalDataSource
import com.dev.githubuser.data.remote.RemoteDataSource
import com.dev.githubuser.domain.IUserRepository
import com.dev.githubuser.domain.User
import com.dev.githubuser.util.DataMapper
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository private constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IUserRepository {

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
                        localDataSource: LocalDataSource,
                        remoteDataSource: RemoteDataSource): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(localDataSource, remoteDataSource)
            }
    }

    override fun getFavoriteUsers(): Flowable<List<User>> = localDataSource.getFavoriteUsers().map {
        DataMapper.mapEntitiesToDomain(it)
    }

    override fun insert(user: User) {
        localDataSource.insert(DataMapper.mapDomainToEntity(user))
    }

    override fun delete(user: String) {
        localDataSource.delete(user)
    }

    override fun isExist(username: String): Flowable<Int> = localDataSource.isExist(username)

    override fun getFollowers(username: String): Flowable<List<User>> {
        return remoteDataSource.getFollowers(username).map { DataMapper.mapResponsesToDomain(it) }
    }

    override fun getFollowing(username: String): Flowable<List<User>> {
        return remoteDataSource.getFollowing(username).map { DataMapper.mapResponsesToDomain(it) }
    }

    override fun findUser(username: String): Flowable<List<User>> {
        return remoteDataSource.findUser(username).map { DataMapper.mapResponsesToDomain(it.items) }
    }

    override fun getUser(username: String): Flow<User> {
        return remoteDataSource.getUser(username).map { DataMapper.mapResponseToDomain(it) }
    }
}