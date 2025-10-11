package com.example.pos_warung.domain.model

data class Product(
    val id: Long? = null,
    val name: String,
    val sku: String,
    val price: Double,
    val stock: Int,
    val imageUrl: String? = null,
)
