package com.dev.githubuser.data.local

import com.dev.githubuser.data.local.db.UserDao
import com.dev.githubuser.data.local.db.UserEntity
import io.reactivex.Flowable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LocalDataSource private constructor(private val userDao: UserDao
) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(userDao: UserDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(userDao)
            }
    }

    fun getFavoriteUsers(): Flowable<List<UserEntity>> {
        return userDao.getFavoriteUsers()
    }

    fun insert(user: UserEntity) {
        executorService.execute { userDao.insert(user) }
    }

    fun delete(user: String) {
        executorService.execute { userDao.delete(user) }
    }

    fun isExist(username: String): Flowable<Int> {
        return userDao.count(username)
    }


}