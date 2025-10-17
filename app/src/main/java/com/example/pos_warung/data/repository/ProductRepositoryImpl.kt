package com.example.pos_warung.data.repository

import com.example.pos_warung.data.local.dao.ProductDao
import com.example.pos_warung.data.local.entity.ProductEntity
import com.example.pos_warung.domain.common.Result
import com.example.pos_warung.domain.model.Product
import com.example.pos_warung.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao,
) : ProductRepository {

    private fun ProductEntity.toProduct(): Product {
        return Product(
            id = this.id,
            name = this.name,
            sku = this.sku,
            price = this.price,
            stock = this.stock,
            imageUrl = this.imageUrl
        )
    }

    private fun Product.toEntity(): ProductEntity {
        return ProductEntity(
            id = this.id ?: 0L,
            name = this.name,
            sku = this.sku,
            price = this.price,
            stock = this.stock,
            imageUrl = this.imageUrl
        )
    }

    override fun getProducts(): Flow<Result<List<Product>>> {
        return flow {
            try {
                productDao.getAllProducts().collect { entities ->
                    val products = entities.map { it.toProduct() }

                    emit(Result.Success(products))
                }
            } catch (e: Exception) {

                emit(Result.Error(message = "Gagal memuat produk", exception = e))
            }
        }
    }
    override fun getProductById(productId: Long): Flow<Result<Product>> {
        return flow {
            emit(Result.Loading) // Emit state Loading
            try {
                productDao.getProductById(productId).collect { entity ->
                    if (entity != null) {
                        emit(Result.Success(entity.toProduct()))
                    } else {
                        emit(Result.Error(message = "Produk dengan ID $productId tidak ditemukan"))
                    }
                }
            } catch (e: Exception) {
                emit(Result.Error(message = "Gagal memuat produk", exception = e))
            }
        }

    }

    override suspend fun addProduct(product: Product): Result<Unit> {
        return try {
            productDao.insertProduct(product.toEntity())
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(message = "Gagal menambahkan produk ${e.message}", exception = e)
        }
    }

    override suspend fun updateProduct(product: Product): Result<Unit> {
        return try {
            productDao.updateProduct(product.toEntity())
            Result.Success(Unit)
        }catch (e: Exception) {
            Result.Error(message = "Gagal memperbarui produk ${e.message}", exception = e)
        }
    }

    override suspend fun deleteProduct(product: Product): Result<Unit> {
        try {
            productDao.deleteProduct(product.toEntity())
            return Result.Success(Unit)
        } catch (e: Exception) {
            return Result.Error(message = "Gagal menghapus produk ${e.message}", exception = e)
        }
    }
}