package com.example.pos_warung.domain.repository

import com.example.pos_warung.domain.model.Transaction
import com.example.pos_warung.domain.common.Result
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun addTransaction(transaction: Transaction): Result<Long>
    fun getTransactions(): Flow<List<Transaction>>
    fun getTransactionById(transactionId: Long): Flow<Transaction?>
    fun getTransactionByDates(startDate: String, endDate: String): Flow<List<Transaction>>
}