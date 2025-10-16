package com.example.pos_warung.presentation.ui.components.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.pos_warung.domain.model.Product
import com.example.pos_warung.presentation.ui.components.common.POSButton
import com.example.pos_warung.presentation.ui.components.common.PosTextField

@Composable
fun ProductForm(
    product: Product?,
    onSave: (Product) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember(product?.id) { mutableStateOf(product?.name ?: "") }
    var sku by remember(product?.id) { mutableStateOf(product?.sku ?: "") }
    var priceText by remember(product?.id) { mutableStateOf(product?.price?.toString() ?: "") }
    var stockText by remember(product?.id) { mutableStateOf(product?.stock?.toString() ?: "") }

    var nameError by remember(product?.id) { mutableStateOf<String?>(null) }
    var priceError by remember(product?.id) { mutableStateOf<String?>(null) }
    var stockError by remember(product?.id) { mutableStateOf<String?>(null) }

    fun validateInput(): Boolean {
        var isValid = true
        if (name.isBlank()) {
            nameError = "Nama produk tidak boleh kosong"
            isValid = false
        } else {
            nameError = null
        }
        val price = priceText.toDoubleOrNull()
        if (price == null || price < 0) {
            priceError = "Harga tidak valid"
            isValid = false
        } else {
            priceError = null

        }
        val stock = stockText.toIntOrNull()
        if (stock == null || stock < 0) {
            stockError = "Stok tidak valid"
            isValid = false
        } else {
            stockError = null
        }
        return isValid
    }
    val handledSave = {
        if (validateInput()){
            val newProduct = Product(
                id = product?.id,
                name = name,
                sku = sku,
                price = priceText.toDouble(),
                stock = stockText.toInt()
            )
            onSave(newProduct)
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PosTextField(
            value = name,
            onValueChange = { name = it },
            label = "Nama Produk",
            placeholder = "Masukkan nama produk",
            isError = nameError != null,
            errorMessage = nameError ?: ""
        )
        PosTextField(
            value = sku,
            onValueChange = { sku = it },
            label = "SKU",
            placeholder = "Masukkan SKU",
        )
        PosTextField(
            value = priceText,
            onValueChange = { priceText = it },
            label = "Harga",
            placeholder = "0",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = priceError != null,
            errorMessage = priceError ?: ""
        )
        PosTextField(
            value = stockText,
            onValueChange = { stockText = it },
            label = "Stok",
            placeholder = "0",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = stockError != null,
            errorMessage = stockError ?: ""
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            POSButton(
                text = "Batal",
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            )
            POSButton(
                text = if (product == null) "Tambah Produk" else "Update Produk",
                onClick = handledSave,
                modifier = Modifier.weight(1f)
            )
        }
    }
}


