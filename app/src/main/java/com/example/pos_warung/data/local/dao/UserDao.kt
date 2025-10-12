package com.example.pos_warung.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pos_warung.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsernameOnce(username: String): UserEntity?

    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserByUsernameFlow(username: String): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: Long) : Flow<UserEntity?>

    @Insert
    suspend fun insertUser(user: UserEntity) : Long
}