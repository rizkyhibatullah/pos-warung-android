package com.example.pos_warung.di

import com.example.pos_warung.data.local.dao.ProductDao
import com.example.pos_warung.data.local.dao.TransactionDao
import com.example.pos_warung.data.local.dao.UserDao
import com.example.pos_warung.data.repository.ProductRepositoryImpl
import com.example.pos_warung.data.repository.TransactionRepositoryImpl
import com.example.pos_warung.data.repository.UserRepositoryImpl
import com.example.pos_warung.domain.repository.ProductRepository
import com.example.pos_warung.domain.repository.TransactionRepository
import com.example.pos_warung.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }

    @Provides
    @Singleton
    fun provideProductRepository(productDao: ProductDao): ProductRepository {
        return ProductRepositoryImpl(productDao)
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(transactionDao: TransactionDao): TransactionRepository {
        return TransactionRepositoryImpl(transactionDao)
    }



}