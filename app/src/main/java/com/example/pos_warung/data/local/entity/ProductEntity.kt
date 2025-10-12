package com.example.pos_warung.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "product_name")
    val name: String,

    @ColumnInfo(name = "sku")
    val sku: String,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "stock")
    val stock: Int,

    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null
)
