package com.example.pos_warung.domain.repository

import com.example.pos_warung.domain.model.Transaction
import com.example.pos_warung.domain.common.Result
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun addTransaction(transaction: Transaction): Result<Long>
    fun getTransactions(): Flow<Result<List<Transaction>>>
    fun getTransactionById(transactionId: Long): Flow<Transaction?>
    fun getTransactionsByDateRange(startDate: Long, endDate: Long): Flow<Result<List<Transaction>>>
}