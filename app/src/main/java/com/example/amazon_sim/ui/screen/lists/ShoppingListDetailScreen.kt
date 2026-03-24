package com.example.amazon_sim.ui.screen.lists

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.data.CartManager
import com.example.amazon_sim.data.repository.ProductDetailRepositoryImpl
import com.example.amazon_sim.domain.model.Product
import com.example.amazon_sim.domain.model.ShoppingList
import com.example.amazon_sim.ui.screen.lists.components.DetailTitleRow
import com.example.amazon_sim.ui.screen.lists.components.ProductDetailCard
import com.example.amazon_sim.ui.theme.AmazonProfileTopBackground
import kotlinx.coroutines.launch

@Composable
fun ShoppingListDetailScreen(
    shoppingList: ShoppingList?,
    products: List<Product>,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    val context = LocalContext.current
    val detailRepo = remember { ProductDetailRepositoryImpl() }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    androidx.compose.material3.Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .border(1.dp, Color(0xFFCCCCCC), RoundedCornerShape(8.dp))
                        .clickable(onClick = onSearchClick)
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = Color(0xFF666666),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Search Amazon",
                        fontSize = 14.sp,
                        color = Color(0xFF999999)
                    )
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.CameraAlt, "Camera", tint = Color.Gray, modifier = Modifier.size(24.dp))
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.Mic, "Voice", tint = Color.Gray, modifier = Modifier.size(24.dp))
                }
            }

            // Content
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                // Title row
                item {
                    DetailTitleRow(
                        listName = shoppingList?.listName ?: "",
                        onAddItemClick = {
                            Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
                        },
                        onMoreClick = {
                            Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                item {
                    HorizontalDivider(color = Color(0xFFDDDDDD), thickness = 1.dp)
                }

                // Product cards
                items(products) { product ->
                    val detail = detailRepo.getProductById(product.id)

                    val priceOption = detail?.specGroups
                        ?.lastOrNull { group ->
                            val defaultId = detail.defaultSpecOptionIds[group.dimensionName]
                            group.options.find { it.id == defaultId }?.price != null
                        }?.let { group ->
                            val defaultId = detail.defaultSpecOptionIds[group.dimensionName]
                            group.options.find { it.id == defaultId }
                        }

                    ProductDetailCard(
                        product = product,
                        detail = detail,
                        onAddToCartClick = {
                            CartManager.init(context)
                            CartManager.addToCart(
                                context = context,
                                productName = product.name,
                                price = priceOption?.price ?: product.price,
                                quantity = 1,
                                placeholderColor = product.colorSwatches.firstOrNull() ?: 0xFFCCCCCC,
                                imageAssetPath = product.imageUrl,
                                productId = product.id
                            )
                            scope.launch {
                                snackbarHostState.showSnackbar("Added to cart")
                            }
                        },
                        onMoreClick = {
                            Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
                        }
                    )
                    HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}
