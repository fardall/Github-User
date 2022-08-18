package com.dev.githubuser.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(userEntity: UserEntity): Completable

    @Query("DELETE from UserEntity WHERE username = :username")
    fun delete(username: String)

    @Query("SELECT * from UserEntity ORDER BY id ASC")
    fun getFavoriteUsers(): Flowable<List<UserEntity>>

    @Query("SELECT COUNT() FROM UserEntity WHERE username = :username")
    fun count(username: String): Flowable<Int>
    
}