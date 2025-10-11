package com.example.pos_warung.domain.model

data class TransactionItem (
    val productId: Long,
    val productName: String,
    val quantity: Int,
    val pricePerUnit: Double,
)