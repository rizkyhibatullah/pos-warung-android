package com.example.pos_warung.domain.usecase.product

import com.example.pos_warung.domain.model.Product
import com.example.pos_warung.domain.repository.ProductRepository
import com.example.pos_warung.domain.common.Result
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(
    val productRepository: ProductRepository
) {
    suspend operator fun invoke(product: Product) : Result<Unit> {
        if (product.name.isBlank()){
            return Result.Error(message = "Nama Produk tidak boleh kosong")
        }
        if (product.id == null){
            return Result.Error(message = "Produk tidak ditemukan")
        }
        if (product.price < 0){
            return Result.Error(message = "Harga Produk tidak boleh negatif")
        }
        if (product.stock < 0){
            return Result.Error(message = "Stok Produk tidak boleh negatif")
        }
        return productRepository.updateProduct(product)
    }
}