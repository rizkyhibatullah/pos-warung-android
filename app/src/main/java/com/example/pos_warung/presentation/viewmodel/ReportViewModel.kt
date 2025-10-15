package com.example.pos_warung.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pos_warung.domain.common.Result
import com.example.pos_warung.domain.model.Transaction
import com.example.pos_warung.domain.usecase.transaction.GetTransactionsByDateRangeUseCase
import com.example.pos_warung.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val getTransactionByDatesUseCase: GetTransactionsByDateRangeUseCase
) : ViewModel(){
    private val _reportState = MutableStateFlow<UiState<List<Transaction>>>(UiState.Idle)
    val reportState: StateFlow<UiState<List<Transaction>>> = _reportState.asStateFlow()

    fun generateReport(startDate: Long, endDate: Long) {
        viewModelScope.launch {
            _reportState.value = UiState.Loading
            getTransactionByDatesUseCase(startDate, endDate).collect { result ->
                when(result){
                    is Result.Loading -> _reportState.value = UiState.Loading
                    is Result.Success -> _reportState.value = UiState.Success(result.data)
                    is Result.Error -> _reportState.value = UiState.Error(result.message ?: "Gagal memuat laporan")
                }
            }
        }
    }
}