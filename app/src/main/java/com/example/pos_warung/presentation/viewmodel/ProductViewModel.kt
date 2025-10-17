package com.example.pos_warung.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pos_warung.domain.common.Result
import com.example.pos_warung.domain.model.Product
import com.example.pos_warung.domain.usecase.product.*
import com.example.pos_warung.presentation.common.UiState
import com.example.pos_warung.presentation.common.UiState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase
) : ViewModel() {

    // State untuk daftar produk
    private val _productsState = MutableStateFlow<UiState<List<Product>>>(UiState.Idle)
    val productsState: StateFlow<UiState<List<Product>>> = _productsState.asStateFlow()

    // State untuk produk yang dipilih (untuk edit/detail)
    private val _selectedProductState = MutableStateFlow<UiState<Product>>(UiState.Idle)
    val selectedProductState: StateFlow<UiState<Product>> = _selectedProductState.asStateFlow()

    // State untuk operasi tunggal (tambah/hapus/update)
    private val _operationState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val operationState: StateFlow<UiState<Unit>> = _operationState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        getProductsUseCase().onEach { result ->
            _productsState.value = when (result) {
                is Result.Loading -> UiState.Loading
                is Result.Success -> UiState.Success(result.data)
                is Result.Error -> UiState.Error(result.message ?: "Terjadi kesalahan")
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Mengambil data produk berdasarkan ID dan memperbarui selectedProductState.
     */
    fun getProductById(productId: Long) {
        getProductByIdUseCase(productId).onEach { result ->
            _selectedProductState.value = when (result) {
                is Result.Loading -> UiState.Loading
                is Result.Success -> UiState.Success(result.data)
                is Result.Error -> UiState.Error(result.message ?: "Gagal memuat produk")
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Membersihkan state produk yang dipilih.
     * Berguna saat keluar dari layar edit/detail.
     */
    fun clearSelectedProduct() {
        _selectedProductState.value = UiState.Idle
    }

    fun addProducts(product: Product) {
        viewModelScope.launch {
            _operationState.value = UiState.Loading
            // Konversi Result menjadi UiState
            _operationState.value = when (val result = addProductUseCase(product)) {
                is Result.Success -> Success(result.data)
                is Result.Error -> Error(result.message ?: "Gagal menambahkan produk")
                else -> UiState.Loading
            }
        }
    }

    fun updateProducts(product: Product) {
        viewModelScope.launch {
            _operationState.value = UiState.Loading
            // Konversi Result menjadi UiState
            _operationState.value = when (val result = updateProductUseCase(product)) {
                is Result.Success -> UiState.Success(result.data)
                is Result.Error -> UiState.Error(result.message ?: "Gagal memperbarui produk")
                else -> UiState.Loading
            }
        }
    }

    fun deleteProducts(product: Product) {
        viewModelScope.launch {
            _operationState.value = UiState.Loading
            // Konversi Result menjadi UiState
            _operationState.value = when (val result = deleteProductUseCase(product)) {
                is Result.Success -> UiState.Success(result.data)
                is Result.Error -> UiState.Error(result.message ?: "Gagal menghapus produk")
                else -> UiState.Loading
            }
        }
    }

    fun onOperationHandled() {
        _operationState.value = UiState.Idle
    }
}