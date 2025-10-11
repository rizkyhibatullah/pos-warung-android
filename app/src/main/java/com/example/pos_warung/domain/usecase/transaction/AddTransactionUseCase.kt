package com.example.pos_warung.domain.usecase.transaction

import com.example.pos_warung.domain.common.Result
import com.example.pos_warung.domain.model.Transaction
import com.example.pos_warung.domain.repository.ProductRepository
import com.example.pos_warung.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(transaction: Transaction): Result<Long> {
    for(item in transaction.transactionItems){
        val product = productRepository.getProductById(item.productId).first()
        if (product == null){
            return Result.Error(message = "Produk dengan ID ${item.productId} tidak ditemukan")
        }
        if (product.stock < item.quantity){
            return Result.Error(message = "Stok Produk dengan ID ${item.productId} tidak mencukupi")
        }
        }
        return transactionRepository.addTransaction(transaction)
    }
}