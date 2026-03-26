package com.example.amazon_sim.ui.screen.interests

import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.domain.model.InterestCategory
import com.example.amazon_sim.domain.model.Product
import com.example.amazon_sim.ui.screen.interests.components.InterestProductCard
import com.example.amazon_sim.ui.theme.AmazonProfileTopBackground
import androidx.compose.foundation.Image

@Composable
fun InterestsScreen(
    categories: List<InterestCategory>,
    selectedCategory: InterestCategory?,
    filteredProducts: List<Product>,
    alexaListProductIds: Set<String>,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onPlusClick: () -> Unit,
    onCategoryClick: (InterestCategory) -> Unit,
    onProductClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top bar
        InterestsTopBar(
            onBackClick = onBackClick,
            onSearchClick = onSearchClick
        )

        // "Interests" title
        Text(
            text = "Interests",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 4.dp)
        )

        // Interest tags horizontal scrolling bar
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            // "+" button
            item {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .border(1.dp, Color(0xFFCCCCCC), CircleShape)
                        .background(Color.White, CircleShape)
                        .clickable(onClick = onPlusClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFF555555)
                    )
                }
            }

            // Interest category tags
            items(categories) { category ->
                val isSelected = category.id == selectedCategory?.id
                InterestTag(
                    category = category,
                    isSelected = isSelected,
                    onClick = { onCategoryClick(category) }
                )
            }
        }

        // Divider
        HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)

        // Category title row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedCategory?.displayName ?: "",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More",
                    tint = Color(0xFF333333)
                )
            }
        }

        // "Results" label
        Text(
            text = "Results",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        // Product grid or empty state
        if (filteredProducts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No products found in this category",
                    fontSize = 14.sp,
                    color = Color(0xFF888888)
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredProducts) { product ->
                    InterestProductCard(
                        product = product,
                        isFavorite = product.id in alexaListProductIds,
                        onClick = { onProductClick(product.id) },
                        onSeeOptionsClick = { onProductClick(product.id) },
                        onFavoriteClick = { onFavoriteClick(product.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun InterestsTopBar(
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AmazonProfileTopBackground)
            .statusBarsPadding()
            .padding(bottom = 8.dp, start = 4.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        // Search bar
        Row(
            modifier = Modifier
                .weight(1f)
                .height(44.dp)
                .background(Color.White, RoundedCornerShape(8.dp))
                .border(1.dp, Color(0xFFCCCCCC), RoundedCornerShape(8.dp))
                .clickable(onClick = onSearchClick)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = Color(0xFF888888),
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Search Amazon",
                fontSize = 15.sp,
                color = Color(0xFF888888),
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Filled.CameraAlt,
                contentDescription = null,
                tint = Color(0xFF888888),
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                imageVector = Icons.Filled.Mic,
                contentDescription = null,
                tint = Color(0xFF888888),
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
private fun InterestTag(
    category: InterestCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) Color(0xFF0066C0) else Color(0xFFCCCCCC)
    val borderWidth = if (isSelected) 2.dp else 1.dp
    val bgColor = if (isSelected) Color(0xFFE3F2FD) else Color.White
    val textColor = if (isSelected) Color(0xFF0066C0) else Color(0xFF333333)
    val textWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal

    if (category.imageAssetPath != null) {
        // Tag with image
        Row(
            modifier = Modifier
                .height(56.dp)
                .border(borderWidth, borderColor, RoundedCornerShape(28.dp))
                .background(bgColor, RoundedCornerShape(28.dp))
                .clip(RoundedCornerShape(28.dp))
                .clickable(onClick = onClick)
                .padding(start = 8.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail image from assets
            val context = LocalContext.current
            val imageBitmap by produceState<androidx.compose.ui.graphics.ImageBitmap?>(
                initialValue = null,
                category.imageAssetPath
            ) {
                value = runCatching {
                    context.assets.open(category.imageAssetPath).use { input ->
                        BitmapFactory.decodeStream(input)?.asImageBitmap()
                    }
                }.getOrNull()
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFEEEEEE))
            ) {
                if (imageBitmap != null) {
                    Image(
                        bitmap = imageBitmap!!,
                        contentDescription = category.displayName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = category.displayName,
                fontSize = 15.sp,
                color = textColor,
                fontWeight = textWeight
            )
        }
    } else {
        // Tag without image
        Box(
            modifier = Modifier
                .height(56.dp)
                .border(borderWidth, borderColor, RoundedCornerShape(28.dp))
                .background(bgColor, RoundedCornerShape(28.dp))
                .clip(RoundedCornerShape(28.dp))
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category.displayName,
                fontSize = 15.sp,
                color = textColor,
                fontWeight = textWeight
            )
        }
    }
}
