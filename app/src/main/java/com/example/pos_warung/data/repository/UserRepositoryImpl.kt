package com.example.pos_warung.data.repository

import com.example.pos_warung.data.local.dao.UserDao
import com.example.pos_warung.data.local.entity.UserEntity
import com.example.pos_warung.domain.common.Result
import com.example.pos_warung.domain.model.User
import com.example.pos_warung.domain.model.UserRole
import com.example.pos_warung.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    private fun UserEntity.toUser(): User {
        return User(
            id = this.id,
            username = this.username,
            password = this.password,
            role = UserRole.valueOf(this.role)
        )
    }

    private fun User.toEntity(): UserEntity {
        return UserEntity(
            id = this.id ?: 0L,
            username = this.username,
            password = this.password,
            role = this.role.name
        )
    }

    override suspend fun login(
        username: String,
        password: String
    ): Result<User> {
        return try {
            val userEntity = userDao.getUserByUsernameOnce(username)
            if (userEntity == null){
                return Result.Error(message = "User not found")
            }
            if (userEntity.password != password){
                return Result.Error(message = "Invalid password")
            }
            return Result.Success(userEntity.toUser())
        }catch (
            e: Exception
        ){
            Result.Error(message = "Login failed", exception = e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return Result.Success(Unit)
    }

    override fun getUserById(userId: Long): Flow<User?> {
        return userDao.getUserById(userId).map { it?.toUser() }
    }

    override fun getUserByUsername(username: String): Flow<User?> {
        return userDao.getUserByUsernameFlow(username).map { it?.toUser() }
    }

    override suspend fun addUsers(users: User): Result<Unit> {
        return try {
            userDao.insertUser(users.toEntity())
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(message = "Gagal menambahkan user ${e.message}", exception = e)
        }
    }
}