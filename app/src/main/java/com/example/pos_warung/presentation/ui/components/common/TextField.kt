package com.example.pos_warung.presentation.ui.components.common

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PosTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    isError: Boolean = false,
    errorMessage: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = modifier,
        placeholder = { Text(text = placeholder) },
        isError = isError,
        supportingText = {
            if (isError && errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        singleLine = true
    )
}

@Preview(showSystemUi = false)
@Composable
fun PreviewPosTextField(){
    PosTextField(
        value = "Indomie",
        onValueChange = {},
        label = "Nama Produk",
        placeholder = "Masukkan nama produk"
    )
}

@Preview(showSystemUi = false)
@Composable
fun PreviewPosTextFieldError(){
    PosTextField(
        value = "",
        onValueChange = {},
        label = "Nama Produk",
        placeholder = "Masukkan nama produk",
        isError = true,
        errorMessage = "Nama produk tidak boleh kosong"
    )
}
