package com.example.pos_warung.di

import android.content.Context
import androidx.room.Room
import com.example.pos_warung.data.local.dao.ProductDao
import com.example.pos_warung.data.local.dao.TransactionDao
import com.example.pos_warung.data.local.dao.UserDao
import com.example.pos_warung.data.local.database.PosDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providePosDatabase(@ApplicationContext context: Context): PosDatabase {
        return Room.databaseBuilder(
            context,
            PosDatabase::class.java,
            "pos_database"
        ).build()
    }

    @Provides
    fun provideProductDao(database: PosDatabase) : ProductDao = database.productDao()

    @Provides
    fun provideUserDao(database: PosDatabase) : UserDao = database.userDao()

    @Provides
    fun provideTransactionDao(database: PosDatabase) : TransactionDao = database.transactionDao()
}