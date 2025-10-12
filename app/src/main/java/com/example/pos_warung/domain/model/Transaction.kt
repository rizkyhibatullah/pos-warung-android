package com.example.pos_warung.domain.model

data class Transaction(
    val id: Long? = null,
    val transactionDate: Long,
    val transactionItems: List<TransactionItem>,
    val totalAmount: Double,
    val cashierId: Long
)
