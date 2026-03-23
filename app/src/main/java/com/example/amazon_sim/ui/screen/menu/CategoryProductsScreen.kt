package com.example.amazon_sim.ui.screen.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.data.repository.ProductDetailRepositoryImpl
import com.example.amazon_sim.domain.model.Product
import com.example.amazon_sim.ui.screen.search.components.ProductResultCard
import com.example.amazon_sim.ui.theme.AmazonProfileTopBackground

@Composable
fun CategoryProductsScreen(
    category: String,
    products: List<Product>,
    onBackClick: () -> Unit,
    onProductClick: (String) -> Unit
) {
    val detailRepo = ProductDetailRepositoryImpl()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .statusBarsPadding()
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AmazonProfileTopBackground)
                .padding(horizontal = 4.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Text(
                text = category,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        if (products.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("\uD83D\uDD0D", fontSize = 48.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No products in \"$category\"",
                    fontSize = 16.sp,
                    color = Color(0xFF888888)
                )
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Text(
                        text = "${products.size} results in \"$category\"",
                        fontSize = 12.sp,
                        color = Color(0xFF888888),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                items(products) { product ->
                    val detail = detailRepo.getProductById(product.id)
                    val firstPriceOption = detail?.specGroups
                        ?.lastOrNull { group ->
                            val defaultId = detail.defaultSpecOptionIds[group.dimensionName]
                            group.options.find { it.id == defaultId }?.price != null
                        }?.let { group ->
                            val defaultId = detail.defaultSpecOptionIds[group.dimensionName]
                            group.options.find { it.id == defaultId }
                        }

                    val originalPrice = firstPriceOption?.originalPrice
                    val deliveryDate = detail?.freeDeliveryDate ?: "Friday, March 27"

                    val colorGroup = detail?.specGroups?.find {
                        it.dimensionName.equals("Color", ignoreCase = true)
                    }
                    val defaultColorId = detail?.defaultSpecOptionIds?.get("Color")
                        ?: detail?.defaultSpecOptionIds?.get("color")
                    val defaultColorOption = colorGroup?.options?.find { it.id == defaultColorId }
                    val defaultColorName = defaultColorOption?.label
                    val extraColorCount = if (colorGroup != null && colorGroup.options.size > 1) {
                        colorGroup.options.size - 1
                    } else 0

                    ProductResultCard(
                        product = product,
                        originalPrice = originalPrice,
                        deliveryDate = deliveryDate,
                        defaultColorName = defaultColorName,
                        extraColorCount = extraColorCount,
                        onClick = { onProductClick(product.id) },
                        onSeeOptionsClick = { onProductClick(product.id) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}
