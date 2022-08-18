package com.dev.githubuser.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(userEntity: UserEntity)

    @Query("DELETE from UserEntity WHERE username = :username")
    fun delete(username: String)

    @Query("SELECT * from UserEntity ORDER BY id ASC")
    fun getFavoriteUsers(): Flowable<List<UserEntity>>

    @Query("SELECT COUNT() FROM UserEntity WHERE username = :username")
    fun count(username: String): Flowable<Int>
    
}