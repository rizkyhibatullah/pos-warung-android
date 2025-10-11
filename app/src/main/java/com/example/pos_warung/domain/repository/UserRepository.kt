package com.example.pos_warung.domain.repository

import com.example.pos_warung.domain.model.User
import com.example.pos_warung.domain.common.Result
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(username: String, password: String): Result<User>
    suspend fun logout(): Result<Unit>
    fun getUserById(userId: Long): Flow<User?>
    fun getUserByUsername(username: String): Flow<User?>
    suspend fun addUsers(users: User): Result<Unit>
}