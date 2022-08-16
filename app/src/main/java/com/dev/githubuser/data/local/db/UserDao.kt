package com.dev.githubuser.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(userEntity: UserEntity)

    @Query("DELETE from UserEntity WHERE username = :username")
    fun delete(username: String)

    @Query("SELECT * from UserEntity ORDER BY id ASC")
    fun getFavoriteUsers(): LiveData<List<UserEntity>>

    @Query("SELECT COUNT() FROM UserEntity WHERE username = :username")
    fun count(username: String): LiveData<Int>
    
}