package com.example.pos_warung.presentation.ui.components.product

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pos_warung.domain.model.Product

@Composable
fun ProductCard(
    product: Product,
    onCardClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onCardClick(product) },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SKU: ${product.sku}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Stok: ${product.stock}",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (product.stock > 10) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        MaterialTheme.colorScheme.error
                    },
                    fontWeight = if (product.stock <= 10) FontWeight.Bold else FontWeight.Normal
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Rp ${"%,.2f".format(product.price)}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductCardPreview() {
    ProductCard(
        product = Product(
            name = "Indomie Goreng Rasa Sambal Matah",
            sku = "SKU-001-XYZ",
            price = 3500.0,
            stock = 50
        ),
        onCardClick = { /* Do nothing */ }
    )
}
@Preview(showBackground = true)
@Composable
private fun ProductCardLowStockPreview() {
    ProductCard(
        product = Product(
            name = "Seduh Kopi Kapal Api",
            sku = "SKU-002-ABC",
            price = 1500.0,
            stock = 5 // Stok rendah untuk menunjukkan perubahan warna
        ),
        onCardClick = { /* Do nothing */ }
    )
}