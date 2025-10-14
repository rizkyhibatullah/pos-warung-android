package com.example.pos_warung.domain.usecase.product

import com.example.pos_warung.domain.model.Product
import com.example.pos_warung.domain.repository.ProductRepository
import com.example.pos_warung.domain.common.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val productRepository: ProductRepository

) {
    operator fun invoke() : Flow<Result<List<Product>>> {
        return productRepository.getProducts()
    }

}