package com.example.pos_warung.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.pos_warung.data.local.dao.ProductDao
import com.example.pos_warung.data.local.dao.TransactionDao
import com.example.pos_warung.data.local.dao.UserDao
import com.example.pos_warung.data.local.entity.ProductEntity
import com.example.pos_warung.data.local.entity.TransactionEntity
import com.example.pos_warung.data.local.entity.TransactionItemEntity
import com.example.pos_warung.data.local.entity.UserEntity
import com.example.pos_warung.domain.model.UserRole

@Database(
    entities = [
        UserEntity::class,
        ProductEntity::class,
        TransactionEntity::class,
        TransactionItemEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(PosDatabase.Converters::class)
abstract class PosDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        private const val DATABASE_NAME = "pos_database"

        @Volatile
        private var INSTANCE: PosDatabase? = null

        fun getInstance(context: Context): PosDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PosDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
    class Converters {
        @TypeConverter
        fun fromUserRole(role: UserRole): String {
            return role.name
        }
        @TypeConverter
        fun toUserRole(role: String): UserRole {
            return UserRole.valueOf(role)
        }
    }

}