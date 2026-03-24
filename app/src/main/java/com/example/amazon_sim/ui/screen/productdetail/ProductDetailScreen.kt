package com.example.amazon_sim.ui.screen.productdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.domain.model.ProductDetailData
import com.example.amazon_sim.domain.model.SpecGroup
import com.example.amazon_sim.ui.screen.productdetail.components.BrandRow
import com.example.amazon_sim.ui.screen.productdetail.components.AddToCartBottomSheet
import com.example.amazon_sim.ui.screen.productdetail.components.CustomerReviewsSection
import com.example.amazon_sim.ui.screen.productdetail.components.DeliverySection
import com.example.amazon_sim.ui.screen.productdetail.components.ImagePagerSection
import com.example.amazon_sim.ui.screen.productdetail.components.PriceSection
import com.example.amazon_sim.ui.screen.productdetail.components.SpecOptionCard
import com.example.amazon_sim.ui.theme.AmazonBuyNowOrange
import com.example.amazon_sim.ui.theme.AmazonCartLinkBlue
import com.example.amazon_sim.ui.theme.AmazonCheckoutYellow
import com.example.amazon_sim.ui.theme.AmazonInStockGreen
import com.example.amazon_sim.ui.theme.AmazonProfileTopBackground
import com.example.amazon_sim.ui.theme.AmazonSearchBarBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: ProductDetailData,
    selectedOptions: Map<String, String>,
    priceInfo: PriceInfo?,
    quantity: Int,
    isFavorite: Boolean,
    deliverToText: String,
    cartItemCount: Int,
    showAddedToCart: Boolean,
    onBackClick: () -> Unit,
    onOptionSelect: (dimensionName: String, optionId: String) -> Unit,
    onQuantityChange: (Int) -> Unit,
    onFavoriteClick: () -> Unit,
    onAddToCartClick: () -> Unit,
    onDismissBottomSheet: () -> Unit,
    onGoToCart: () -> Unit,
    onBuyNowClick: () -> Unit = {},
    onAddToListClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onVisitStoreClick: () -> Unit = {}
) {
    var quantityExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .border(1.dp, AmazonSearchBarBorder, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = Color(0xFF666666),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Search Amazon",
                            fontSize = 14.sp,
                            color = Color(0xFF999999)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AmazonProfileTopBackground
                )
            )
        },
        snackbarHost = { }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {
            // Brand row
            item {
                BrandRow(
                    brandName = product.brandName,
                    rating = product.rating,
                    reviewCount = product.reviewCount,
                    brandLogoPlaceholderColor = product.brandLogoPlaceholderColor,
                    onVisitStoreClick = onVisitStoreClick
                )
            }

            // Product name
            item {
                Text(
                    text = product.name,
                    fontSize = 14.sp,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Sales tag
            item {
                val salesParts = product.salesTag.split(" ", limit = 2)
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(salesParts.firstOrNull() ?: "")
                        }
                        if (salesParts.size > 1) {
                            append(" ${salesParts[1]}")
                        }
                    },
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }

            // Image pager
            item {
                ImagePagerSection(
                    imagePlaceholderColors = product.imagePlaceholderColors,
                    imageAssetPath = product.imageAssetPath,
                    isFavorite = isFavorite,
                    onFavoriteClick = onFavoriteClick,
                    onShareClick = onShareClick
                )
            }

            // Feature buttons row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1A1A1A)
                        ),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("\uD83D\uDD0A Hear the highlights 2:11", fontSize = 12.sp, color = Color.White)
                    }
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, Color(0xFF1A1A1A), RoundedCornerShape(24.dp))
                    ) {
                        Text("↻ VIEW IN 3D", fontSize = 12.sp, color = Color(0xFF1A1A1A))
                    }
                }
            }

            // Divider
            item {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color(0xFFEEEEEE)
                )
            }

            // Spec groups - generic rendering
            items(product.specGroups) { group ->
                SpecGroupSection(
                    group = group,
                    selectedOptionId = selectedOptions[group.dimensionName],
                    onOptionSelect = { optionId -> onOptionSelect(group.dimensionName, optionId) }
                )
            }

            // Divider
            item {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color(0xFFEEEEEE)
                )
            }

            // Price section
            item {
                if (priceInfo != null) {
                    PriceSection(
                        currentPrice = priceInfo.price,
                        originalPrice = priceInfo.originalPrice,
                        discountPercent = priceInfo.discountPercent,
                        installmentMonthly = product.installmentMonthly,
                        installmentMonths = product.installmentMonths
                    )
                }
            }

            // Delivery section
            item {
                Spacer(modifier = Modifier.height(12.dp))
                DeliverySection(
                    freeDeliveryDate = product.freeDeliveryDate,
                    fastestDeliveryDate = product.fastestDeliveryDate,
                    countdownText = product.countdownText,
                    deliverToText = deliverToText
                )
            }

            // In Stock
            item {
                Text(
                    text = "In Stock",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AmazonInStockGreen,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // Quantity dropdown
            item {
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Row(
                        modifier = Modifier
                            .border(1.dp, AmazonSearchBarBorder, RoundedCornerShape(8.dp))
                            .clickable { quantityExpanded = true }
                            .padding(horizontal = 12.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Quantity: $quantity",
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Select quantity",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = quantityExpanded,
                        onDismissRequest = { quantityExpanded = false }
                    ) {
                        (1..10).forEach { qty ->
                            DropdownMenuItem(
                                text = { Text("$qty") },
                                onClick = {
                                    onQuantityChange(qty)
                                    quantityExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Add to Cart button
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        onAddToCartClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AmazonCheckoutYellow
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Add to Cart",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            // Buy Now button
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onBuyNowClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AmazonBuyNowOrange
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Buy Now",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            // Add to List
            item {
                Text(
                    text = "Add to List",
                    fontSize = 14.sp,
                    color = AmazonCartLinkBlue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onAddToListClick() }
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            // Divider
            item {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = Color(0xFFEEEEEE)
                )
            }

            // Customer reviews section
            if (product.reviewSummary != null) {
                item {
                    CustomerReviewsSection(
                        rating = product.rating,
                        reviewCount = product.reviewCount,
                        globalRatingsCount = product.globalRatingsCount,
                        reviewSummary = product.reviewSummary,
                        reviewTags = product.reviewTags,
                        customerPhotoPlaceholderColors = product.customerPhotoPlaceholderColors
                    )
                }
            }

            // Bottom spacing
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }

    // Bottom sheet
    if (showAddedToCart && priceInfo != null) {
        AddToCartBottomSheet(
            productName = product.name,
            price = priceInfo.price,
            quantity = quantity,
            placeholderColor = product.imagePlaceholderColors.firstOrNull() ?: 0xFFCCCCCC,
            cartItemCount = cartItemCount,
            onDismiss = onDismissBottomSheet,
            onGoToCart = onGoToCart
        )
    }
}

@Composable
private fun SpecGroupSection(
    group: SpecGroup,
    selectedOptionId: String?,
    onOptionSelect: (String) -> Unit
) {
    val selectedLabel = group.options.find { it.id == selectedOptionId }?.label ?: ""

    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        // Dimension label with selected value
        Text(
            text = buildAnnotatedString {
                append("${group.dimensionName}: ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(selectedLabel)
                }
            },
            fontSize = 14.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Options
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            group.options.forEach { option ->
                SpecOptionCard(
                    option = option,
                    isSelected = option.id == selectedOptionId,
                    onClick = { onOptionSelect(option.id) }
                )
            }
        }
    }
}
