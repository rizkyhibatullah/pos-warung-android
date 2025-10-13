package com.example.pos_warung.di

import com.example.pos_warung.domain.repository.ProductRepository
import com.example.pos_warung.domain.usecase.product.AddProductUseCase
import com.example.pos_warung.domain.usecase.product.DeleteProductUseCase
import com.example.pos_warung.domain.usecase.product.GetProductByIdUseCase
import com.example.pos_warung.domain.usecase.product.GetProductUseCase
import com.example.pos_warung.domain.usecase.product.UpdateProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    // Product Use Case
    @Provides
    @Singleton
    fun provideAddProductUseCase(productRepository: ProductRepository): AddProductUseCase {
        return AddProductUseCase(productRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateProductUseCase(productRepository: ProductRepository): UpdateProductUseCase {
        return UpdateProductUseCase(productRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteProductUseCase(productRepository: ProductRepository): DeleteProductUseCase {
        return DeleteProductUseCase(productRepository)
    }

    @Provides
    @Singleton
    fun provideGetProductsUseCase(productRepository: ProductRepository): GetProductUseCase {
        return GetProductUseCase(productRepository)
    }

    @Provides
    @Singleton
    fun provideGetProductByIdUseCase(productRepository: ProductRepository): GetProductByIdUseCase {
        return GetProductByIdUseCase(productRepository)
    }


}