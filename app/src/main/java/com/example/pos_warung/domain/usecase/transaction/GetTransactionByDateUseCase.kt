package com.example.pos_warung.domain.usecase.transaction

import com.example.pos_warung.domain.common.Result
import com.example.pos_warung.domain.model.Transaction
import com.example.pos_warung.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionsByDateRangeUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(startDate: Long, endDate: Long): Flow<Result<List<Transaction>>> { // Ubah tipe kembalian
        return repository.getTransactionsByDateRange(startDate, endDate)
    }
}