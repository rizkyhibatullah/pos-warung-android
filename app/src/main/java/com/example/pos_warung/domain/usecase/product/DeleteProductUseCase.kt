package com.example.pos_warung.domain.usecase.product

import com.example.pos_warung.domain.model.Product
import com.example.pos_warung.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    val productRepository: ProductRepository
) {
    suspend operator fun invoke(product: Product) : Result<Unit> {
        return productRepository.deleteProduct(product)
    }
}