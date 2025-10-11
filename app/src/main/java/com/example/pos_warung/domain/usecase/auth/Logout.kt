package com.example.pos_warung.domain.usecase.auth

import com.example.pos_warung.domain.common.Result
import com.example.pos_warung.domain.repository.UserRepository
import javax.inject.Inject

class Logout @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() : Result<Unit> {
        return try {
            userRepository.logout()
        }catch (e: Exception){
            Result.Error(message = "Gagal melakukan logout silahkan coba lagi", exception = e)
        }
    }
}