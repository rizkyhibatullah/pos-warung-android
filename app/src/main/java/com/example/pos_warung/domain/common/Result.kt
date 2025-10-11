package com.example.pos_warung.domain.common

sealed class Result<out T> {
    object Loading : Result<Nothing>()

    data class Success<out T>(val data: T) : Result<T>()

    data class Error(val exception: Throwable? = null, val message: String? = null) : Result<Nothing>()
}