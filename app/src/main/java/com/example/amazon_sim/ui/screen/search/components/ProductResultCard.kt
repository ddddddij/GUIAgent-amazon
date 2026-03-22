package com.example.amazon_sim.ui.screen.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.domain.model.Product
import com.example.amazon_sim.ui.screen.home.components.HomeProductAssetImage
import com.example.amazon_sim.ui.theme.AmazonRatingOrange

@Composable
fun ProductResultCard(
    product: Product,
    originalPrice: Double?,
    deliveryDate: String,
    defaultColorName: String?,
    extraColorCount: Int,
    onClick: () -> Unit,
    onSeeOptionsClick: () -> Unit,
    onQuickAddClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                // Product image with Best Seller badge
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .background(Color.White)
                ) {
                    HomeProductAssetImage(
                        assetPath = product.imageUrl,
                        contentDescription = product.name,
                        fallbackColor = Color(0xFFCCCCCC)
                    )
                    if (product.isBestSeller) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .background(
                                    Color(0xFFFF6600),
                                    RoundedCornerShape(bottomEnd = 4.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Best Seller",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                    // Quick add button
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .size(36.dp)
                            .border(1.dp, Color(0xFFCCCCCC), RoundedCornerShape(8.dp))
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .clickable(onClick = onQuickAddClick),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("\uD83D\uDCCB+", fontSize = 14.sp)
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Product info
                Column(modifier = Modifier.weight(1f)) {
                    // Product name
                    Text(
                        text = product.name,
                        fontSize = 14.sp,
                        color = Color(0xFF333333),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Spec tags
                    if (product.specTags.isNotEmpty()) {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            product.specTags.forEach { tag ->
                                Text(
                                    text = tag,
                                    fontSize = 12.sp,
                                    color = Color(0xFF555555),
                                    modifier = Modifier
                                        .background(Color(0xFFF0F0F0), RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    // Rating row
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = AmazonRatingOrange,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${product.rating}",
                            fontSize = 12.sp,
                            color = AmazonRatingOrange
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "(${String.format("%,d", product.reviewCount)})",
                            fontSize = 12.sp,
                            color = Color(0xFF888888)
                        )
                    }

                    // Sales tag
                    val salesK = product.reviewCount / 1000
                    if (salesK > 0) {
                        Text(
                            text = "${salesK}K+ bought in past month",
                            fontSize = 12.sp,
                            color = Color(0xFF888888)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Price row
                    val priceText = buildAnnotatedString {
                        withStyle(SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, baselineShift = BaselineShift.Superscript)) {
                            append("$")
                        }
                        val parts = String.format("%.2f", product.price).split(".")
                        withStyle(SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)) {
                            append(parts[0])
                        }
                        withStyle(SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, baselineShift = BaselineShift.Superscript)) {
                            append(".${parts[1]}")
                        }
                    }
                    Text(text = priceText, color = Color.Black)

                    // Original price
                    if (originalPrice != null && originalPrice > product.price) {
                        Text(
                            text = "List Price: $${String.format("%.2f", originalPrice)}",
                            fontSize = 12.sp,
                            color = Color(0xFF888888),
                            textDecoration = TextDecoration.LineThrough
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    // Delivery info
                    Text(
                        text = buildAnnotatedString {
                            append("FREE delivery ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(deliveryDate)
                            }
                        },
                        fontSize = 12.sp,
                        color = Color.Black
                    )

                    // Color info
                    if (defaultColorName != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = buildAnnotatedString {
                                    append("Color: ")
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append(defaultColorName)
                                    }
                                },
                                fontSize = 12.sp,
                                color = Color.Black
                            )
                            if (extraColorCount > 0) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "+$extraColorCount other colors/patterns",
                                    fontSize = 12.sp,
                                    color = Color(0xFF888888)
                                )
                            }
                        }
                    }

                    // Color swatches
                    if (product.colorSwatches.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            product.colorSwatches.take(4).forEach { color ->
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(CircleShape)
                                        .background(Color(color))
                                        .border(0.5.dp, Color(0xFFCCCCCC), CircleShape)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // See options button
            OutlinedButton(
                onClick = onSeeOptionsClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(24.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCCCCCC))
            ) {
                Text("See options", fontSize = 14.sp, color = Color(0xFF333333))
            }
        }
    }
}
