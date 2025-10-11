package com.example.pos_warung.domain.usecase.product

import com.example.pos_warung.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(productId: Long) = productRepository.getProductById(productId)
}