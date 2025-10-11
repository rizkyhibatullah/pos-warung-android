package com.example.pos_warung.domain.usecase.auth

import com.example.pos_warung.domain.repository.UserRepository
import javax.inject.Inject
import com.example.pos_warung.domain.common.Result
import com.example.pos_warung.domain.model.User

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String, password: String) : Result<User> {
        if (username.isBlank() || password.isBlank() ) {
            return Result.Error(message = "Username atau password tidak boleh kosong")
        }
        return userRepository.login(username, password)
    }
}