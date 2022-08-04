package com.dev.githubuser.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Query("DELETE from user WHERE username = :username")
    fun delete(username: String)

    @Query("SELECT * from user ORDER BY id ASC")
    fun getFavoriteUsers(): LiveData<List<User>>

    @Query("SELECT COUNT() FROM user WHERE username = :username")
    fun count(username: String): LiveData<Int>
    
}