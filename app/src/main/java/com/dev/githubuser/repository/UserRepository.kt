package com.dev.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dev.githubuser.db.User
import com.dev.githubuser.db.UserDao
import com.dev.githubuser.db.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val userDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        userDao = db.userDao()
    }
    fun getFavoriteUsers(): LiveData<List<User>> = userDao.getFavoriteUsers()

    fun insert(user: User) {
        executorService.execute { userDao.insert(user) }
    }
    fun delete(user: String) {
        executorService.execute { userDao.delete(user) }
    }

    fun isExist(username: String): LiveData<Int> = userDao.count(username)
}