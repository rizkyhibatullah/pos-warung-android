package com.example.pos_warung.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pos_warung.domain.common.Result
import com.example.pos_warung.domain.model.Product
import com.example.pos_warung.domain.usecase.product.AddProductUseCase
import com.example.pos_warung.domain.usecase.product.DeleteProductUseCase
import com.example.pos_warung.domain.usecase.product.GetProductByIdUseCase
import com.example.pos_warung.domain.usecase.product.GetProductUseCase
import com.example.pos_warung.domain.usecase.product.UpdateProductUseCase
import com.example.pos_warung.presentation.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val addProductUseCase: AddProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val getProductUseCase: GetProductUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {
    private val _productState = MutableStateFlow<UiState<List<Product>>>(UiState.Idle)
    val productState: StateFlow<UiState<List<Product>>> = _productState.asStateFlow()

    private val _operationState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val operationState: StateFlow<UiState<Unit>> = _operationState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            getProductUseCase().collect { result ->
                when (result) {
                    is Result.Loading -> _productState.value = UiState.Loading
                    is Result.Success -> _productState.value = UiState.Success(result.data)
                    is Result.Error -> _productState.value = UiState.Error(result.message ?: "Terjadi kesalahan")
                }
            }
        }
    }

    fun addProducts(product: Product) {
        viewModelScope.launch {
            _operationState.value = UiState.Loading
            when (val result = addProductUseCase(product)) {
                is Result.Success -> _operationState.value = UiState.Success(Unit)
                is Result.Error -> _operationState.value =
                    UiState.Error(result.message ?: "Gagal menambahkan produk")

                else -> {}
            }
        }
    }

    fun updateProducts(product: Product) {
        viewModelScope.launch {
            _operationState.value = UiState.Loading
            when (val result = updateProductUseCase(product)) {
                is Result.Success -> _operationState.value = UiState.Success(Unit)
                is Result.Error -> _operationState.value =
                    UiState.Error(result.message ?: "Gagal update produk")

                else -> {}
            }
        }
    }

    fun deleteProducts(product: Product) {
        viewModelScope.launch {
            _operationState.value = UiState.Loading
            when (val result = deleteProductUseCase(product)) {
                is Result.Success -> _operationState.value = UiState.Success(Unit)
                is Result.Error -> _operationState.value =
                    UiState.Error(result.message ?: "Gagal menghapus produk")

                else -> {}
            }
        }

    }
    fun onOperationHandled() {
        _operationState.value = UiState.Idle
    }
}