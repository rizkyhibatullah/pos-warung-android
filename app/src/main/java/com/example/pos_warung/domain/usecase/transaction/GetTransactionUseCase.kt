package com.example.pos_warung.domain.usecase.transaction

import com.example.pos_warung.domain.model.Transaction
import com.example.pos_warung.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    operator fun invoke() : Flow<List<Transaction>> = transactionRepository.getTransactions()
}