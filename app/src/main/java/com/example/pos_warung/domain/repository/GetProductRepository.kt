package com.example.pos_warung.domain.repository

import com.example.pos_warung.domain.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductRepository @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(): Flow<List<Product>> {
        return productRepository.getProducts()

    }
}