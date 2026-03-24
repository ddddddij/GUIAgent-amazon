package com.example.amazon_sim.ui.screen.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.data.CartManager
import com.example.amazon_sim.data.repository.ProductDetailRepositoryImpl
import com.example.amazon_sim.domain.model.Product
import com.example.amazon_sim.domain.model.ShoppingList
import com.example.amazon_sim.ui.screen.lists.components.CreateListBottomSheet
import com.example.amazon_sim.ui.screen.lists.components.ListCard
import com.example.amazon_sim.ui.screen.lists.components.SavedProductCard
import com.example.amazon_sim.ui.theme.AmazonProfileTopBackground
import kotlinx.coroutines.launch
import android.widget.Toast

@Composable
fun ListsScreen(
    lists: List<ShoppingList>,
    allSavedProducts: List<Pair<Product, String>>,
    newListNameSuggestion: String,
    allProducts: List<Product>,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onListCardClick: (String) -> Unit,
    onCreateList: (String) -> Unit
) {
    val context = LocalContext.current
    val detailRepo = remember { ProductDetailRepositoryImpl() }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showCreateSheet by remember { mutableStateOf(false) }

    androidx.compose.material3.Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.White
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Lists and Registries",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = { showCreateSheet = true },
                            modifier = Modifier
                                .size(40.dp)
                                .border(1.dp, Color(0xFFCCCCCC), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Create list",
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                // List cards row
                item {
                    LazyRow(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(lists) { list ->
                            val coverImage = if (list.productIds.isNotEmpty()) {
                                allProducts.find { it.id == list.productIds.first() }?.imageUrl
                            } else null

                            ListCard(
                                listName = list.listName,
                                coverImagePath = coverImage,
                                isAlexaList = list.listId == "list_alexa",
                                onClick = { onListCardClick(list.listId) }
                            )
                        }
                    }
                }

                // See all
                item {
                    Text(
                        text = "See all",
                        fontSize = 14.sp,
                        color = Color(0xFF0066C0),
                        modifier = Modifier
                            .padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
                            .clickable {
                                Toast
                                    .makeText(context, "Coming soon", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    )
                }

                // Divider
                item {
                    HorizontalDivider(color = Color(0xFFDDDDDD), thickness = 1.dp)
                }

                // All saves header
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "All saves",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Add item",
                            fontSize = 14.sp,
                            color = Color(0xFF0066C0),
                            modifier = Modifier.clickable {
                                Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }

                // Two-column product grid (chunked to avoid LazyVerticalGrid nesting)
                val chunkedProducts = allSavedProducts.chunked(2)
                items(chunkedProducts.size) { index ->
                    val row = chunkedProducts[index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        row.forEach { (product, listName) ->
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

                            SavedProductCard(
                                product = product,
                                originalPrice = originalPrice,
                                deliveryDate = deliveryDate,
                                savedInListName = listName,
                                onAddToCartClick = {
                                    CartManager.init(context)
                                    CartManager.addToCart(
                                        context = context,
                                        productName = product.name,
                                        price = firstPriceOption?.price ?: product.price,
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
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        // Fill remaining space if odd number
                        if (row.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }

    // Create list bottom sheet
    if (showCreateSheet) {
        CreateListBottomSheet(
            defaultName = newListNameSuggestion,
            onDismiss = { showCreateSheet = false },
            onCreate = { name ->
                onCreateList(name)
                showCreateSheet = false
            },
            onEmptyNameError = {
                Toast.makeText(context, "List name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        )
    }
}
