package com.example.pos_warung.domain.repository

import com.example.pos_warung.domain.model.Product
import com.example.pos_warung.domain.common.Result
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<List<Product>>
    fun getProductById(productId: Long): Flow<Product?>
    suspend fun addProduct(product: Product): Result<Unit>
    suspend fun updateProduct(product: Product): Result<Unit>
    suspend fun deleteProduct(product: Product): Result<Unit>
}