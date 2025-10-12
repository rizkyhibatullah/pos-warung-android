package com.example.pos_warung.data.local.dao

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import com.example.pos_warung.data.local.entity.TransactionEntity
import com.example.pos_warung.data.local.entity.TransactionItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    data class TransactionWithItems(
        @Embedded val transaction: TransactionEntity,
        @Relation(
            parentColumn = "id",
            entityColumn = "transaction_id"
        )
        val items: List<TransactionItemEntity>
    )

    @Transaction
    @Query("SELECT * FROM transactions ORDER BY transaction_date DESC")
    fun getAllTransactionsWithItems(): Flow<List<TransactionWithItems>>

    @Transaction
    @Query("SELECT * FROM transactions WHERE id = :transactionId")
    fun getTransactionWithItemsById(transactionId: Long): Flow<TransactionWithItems?>

    @Transaction
    suspend fun insertTransactionWithItems(
        transaction: TransactionEntity,
        items: List<TransactionItemEntity>
    ): Long {
        val transactionId = insertTransaction(transaction)
        val itemsWithTransactionId = items.map { it.copy(transactionId = transactionId) }
        insertAllItems(itemsWithTransactionId)
        return transactionId
    }

    @Insert
    suspend fun insertTransaction(transaction: TransactionEntity): Long
    @Insert
    suspend fun insertAllItems(items: List<TransactionItemEntity>)


}