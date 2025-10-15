package com.example.pos_warung.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.pos_warung.domain.model.Product
import com.example.pos_warung.domain.model.TransactionItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class CartItem (val product: Product, var quantity: Int)

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice.asStateFlow()

    fun addToCart(product: Product) {
        val currentItems = _cartItems.value.toMutableList()
        val existingItem = _cartItems.value.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            currentItems.add(CartItem(product, 1))
        }
        _cartItems.value = currentItems
        calculateTotal()
    }
    fun removeFromCart(cartItem: CartItem)  {
        val currentItems = _cartItems.value.toMutableList()
        currentItems.remove(cartItem)
        _cartItems.value = currentItems
        calculateTotal()
    }
    fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(cartItem)
            return
        }
        val currentItems = _cartItems.value.toMutableList()
        val item = currentItems.find { it.product.id == cartItem.product.id }
        item?.quantity = newQuantity
        _cartItems.value = currentItems
        calculateTotal()
    }
    fun clearCart(){
        _cartItems.value = emptyList()
        _totalPrice.value = 0.0
    }
    private fun calculateTotal() {
        _totalPrice.value = _cartItems.value.sumOf { it.product.price * it.quantity }
    }
    fun getTransactionItems() : List<TransactionItem> {
        return _cartItems.value.map {
            TransactionItem(
                productId = it.product.id!!,
                productName = it.product.name,
                quantity = it.quantity,
                pricePerUnit = it.product.price
            )
        }
    }
}