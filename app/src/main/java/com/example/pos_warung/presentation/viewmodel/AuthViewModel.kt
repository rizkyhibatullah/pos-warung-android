package com.example.pos_warung.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pos_warung.domain.common.Result
import com.example.pos_warung.domain.model.User
import com.example.pos_warung.domain.usecase.auth.LoginUseCase
import com.example.pos_warung.domain.usecase.auth.Logout
import com.example.pos_warung.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val logout: Logout
) : ViewModel() {
    private val _loginState = MutableStateFlow<UiState<User>>(UiState.Idle)
    val loginState: StateFlow<UiState<User>> = _loginState.asStateFlow()

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _loginState.value = UiState.Error(message = "Username atau password tidak boleh kosong")
            return
        }
        viewModelScope.launch()
        {
            _loginState.value = UiState.Loading
            when (val result = loginUseCase(username, password)) {
                is Result.Success -> _loginState.value = UiState.Success(result.data)
                is Result.Error -> _loginState.value = UiState.Error(result.message ?: "Gagal melakukan login")
                else -> {}
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            when(val result = logout()){
                is Result.Success -> {_loginState.value = UiState.Idle}
                is Result.Error -> {
                    _loginState.value = println("Gagal melakukan logout")
                }
                else -> {}
            }
        }
    }

    fun onLoginHandled() {
        _loginState.value = UiState.Idle
    }



}