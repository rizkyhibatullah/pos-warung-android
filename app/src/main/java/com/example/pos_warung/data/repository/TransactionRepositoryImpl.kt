package com.example.pos_warung.data.repository

import com.example.pos_warung.data.local.dao.TransactionDao
import com.example.pos_warung.data.local.entity.TransactionEntity
import com.example.pos_warung.data.local.entity.TransactionItemEntity
import com.example.pos_warung.domain.common.Result
import com.example.pos_warung.domain.model.Transaction
import com.example.pos_warung.domain.model.TransactionItem
import com.example.pos_warung.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepository {

    private fun TransactionDao.TransactionWithItems.toTransaction(): Transaction {
        return Transaction(
            id = this.transaction.id,
            transactionDate = this.transaction.transactionDate,
            transactionItems = this.items.map {
                TransactionItem(
                    productId = it.productId,
                    productName = it.productName,
                    quantity = it.quantity,
                    pricePerUnit = it.pricePerUnit
                )
            },
            totalAmount = this.transaction.totalAmount,
            cashierId = this.transaction.cashierId
        )
    }

    override suspend fun addTransaction(transaction: Transaction): Result<Long> {
        return try {
            val transactionEntity = TransactionEntity(
                id = 0,
                transactionDate = transaction.transactionDate,
                totalAmount = transaction.totalAmount,
                cashierId = transaction.cashierId
            )
            val itemEntities = transaction.transactionItems.map {
                TransactionItemEntity(
                    id = 0,
                    transactionId = 0,
                    productId = it.productId,
                    productName = it.productName,
                    quantity = it.quantity,
                    pricePerUnit = it.pricePerUnit
                )
            }
            val transactionId = transactionDao.insertTransactionWithItems(transactionEntity, itemEntities)
            Result.Success(transactionId)
        } catch (e: Exception) {
            Result.Error(message = "Gagal menambahkan transaksi ${e.message}", exception = e)
            }
        }

    override fun getTransactions(): Flow<Result<List<Transaction>>> {
        return flow {
            try {
                transactionDao.getAllTransactionsWithItems().collect { transactionWithItemsList ->
                    // Ubah List<TransactionWithItems> menjadi List<Transaction>
                    val transactions = transactionWithItemsList.map { it.toTransaction() }
                    // Emit sebagai Result.Success
                    emit(Result.Success(transactions))
                }
            } catch (e: Exception) {
                // Tangkap error dan emit sebagai Result.Error
                emit(Result.Error(message = "Gagal memuat transaksi", exception = e))
            }
        }
    }

    override fun getTransactionById(transactionId: Long): Flow<Transaction?> {
        return transactionDao.getTransactionWithItemsById(transactionId).map { it?.toTransaction() }
    }

    override fun getTransactionsByDateRange(startDate: Long, endDate: Long): Flow<Result<List<Transaction>>> {
        return flow {
            try {
                transactionDao.getTransactionsWithItemsByDateRange(startDate, endDate).collect { transactionWithItemsList ->
                    // Ubah List<TransactionWithItems> menjadi List<Transaction>
                    val transactions = transactionWithItemsList.map { it.toTransaction() }
                    // Emit sebagai Result.Success
                    emit(Result.Success(transactions))
                }
            } catch (e: Exception) {
                // Tangkap error dan emit sebagai Result.Error
                emit(Result.Error(message = "Gagal menghasilkan laporan", exception = e))
            }
        }
    }
}

