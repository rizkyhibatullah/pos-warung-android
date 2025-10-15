package com.example.pos_warung.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pos_warung.domain.common.Result
import com.example.pos_warung.domain.model.Transaction
import com.example.pos_warung.domain.model.TransactionItem
import com.example.pos_warung.domain.usecase.transaction.AddTransactionUseCase
import com.example.pos_warung.domain.usecase.transaction.GetTransactionUseCase
import com.example.pos_warung.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase,
    private val getTransactionsUseCase: GetTransactionUseCase
) : ViewModel(){
    private val _transactionsState = MutableStateFlow<UiState<List<Transaction>>>(UiState.Idle)
    val transactionsState: StateFlow<UiState<List<Transaction>>> = _transactionsState.asStateFlow()

    private val _checkoutState = MutableStateFlow<UiState<Long>>(UiState.Idle)
    val checkoutState: StateFlow<UiState<Long>> = _checkoutState.asStateFlow()

    init {
        loadTransactions()
    }

    fun loadTransactions(){
        viewModelScope.launch {
            getTransactionsUseCase().collect{result ->
                when(result){
                    is Result.Loading -> _transactionsState.value = UiState.Loading
                    is Result.Success -> _transactionsState.value = UiState.Success(result.data)
                    is Result.Error -> _transactionsState.value = UiState.Error(result.message ?: "Gagal memuat data transaksi")
                }

            }
        }
    }

    fun checkout(items: List<TransactionItem>, totalAmount: Double, cashierId: Long){
        val newTransaction = Transaction(
            id = null,
            transactionDate = System.currentTimeMillis(),
            transactionItems = items,
            totalAmount = totalAmount,
            cashierId = cashierId
        )

        viewModelScope.launch {
            _checkoutState.value = UiState.Loading
            when(val result = addTransactionUseCase(newTransaction)){
                is Result.Success -> _checkoutState.value = UiState.Success(result.data)
                is Result.Error -> _checkoutState.value = UiState.Error(result.message ?: "Gagal melakukan checkout")
                else -> {}
            }
        }
    }

    fun onCheckoutHandled(){
        _checkoutState.value = UiState.Idle
    }
}