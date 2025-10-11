package com.example.pos_warung.domain.usecase.transaction

import com.example.pos_warung.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTransactionByDateUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    operator fun invoke(startDate: String, endDate: String) = transactionRepository.getTransactionByDates(startDate, endDate)
}