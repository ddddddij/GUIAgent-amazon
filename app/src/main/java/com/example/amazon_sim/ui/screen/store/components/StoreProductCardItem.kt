package com.example.amazon_sim.ui.screen.store.components

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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import com.example.amazon_sim.ui.screen.home.components.HomeProductAssetImage
import com.example.amazon_sim.ui.screen.store.StoreProductCard
import com.example.amazon_sim.ui.theme.AmazonCheckoutYellow
import com.example.amazon_sim.ui.theme.AmazonDealRed
import com.example.amazon_sim.ui.theme.AmazonRatingOrange

@Composable
fun StoreProductCardItem(
    card: StoreProductCard,
    onClick: () -> Unit,
    onAddToCartClick: () -> Unit,
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
                // Product image
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .background(Color.White)
                ) {
                    HomeProductAssetImage(
                        assetPath = card.imageAssetPath,
                        contentDescription = card.cardTitle,
                        fallbackColor = Color(0xFFCCCCCC)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Product info
                Column(modifier = Modifier.weight(1f)) {
                    // Product name
                    Text(
                        text = card.cardTitle,
                        fontSize = 14.sp,
                        color = Color(0xFF333333),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    // Variant subtitle
                    if (card.variantLabel.isNotEmpty()) {
                        Text(
                            text = card.variantLabel,
                            fontSize = 12.sp,
                            color = Color(0xFF888888)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

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
                            text = "${card.rating}",
                            fontSize = 12.sp,
                            color = AmazonRatingOrange
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "(${String.format("%,d", card.reviewCount)})",
                            fontSize = 12.sp,
                            color = Color(0xFF888888)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Discount + price row
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (card.discountPercent > 0) {
                            Text(
                                text = "-${card.discountPercent}%",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = AmazonDealRed
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                        val priceText = buildAnnotatedString {
                            withStyle(SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, baselineShift = BaselineShift.Superscript)) {
                                append("$")
                            }
                            val parts = String.format("%.2f", card.currentPrice).split(".")
                            withStyle(SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)) {
                                append(parts[0])
                            }
                            withStyle(SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, baselineShift = BaselineShift.Superscript)) {
                                append(".${parts[1]}")
                            }
                        }
                        Text(text = priceText, color = Color.Black)
                    }

                    // Original price
                    if (card.originalPrice > card.currentPrice) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "List Price: $${String.format("%.2f", card.originalPrice)}",
                                fontSize = 12.sp,
                                color = Color(0xFF888888),
                                textDecoration = TextDecoration.LineThrough
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = null,
                                tint = Color(0xFF888888),
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    // Delivery date
                    Text(
                        text = buildAnnotatedString {
                            append("Get it by ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(card.deliveryDate)
                            }
                        },
                        fontSize = 12.sp,
                        color = Color.Black
                    )

                    // Color swatches
                    if (card.colorSwatches.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            card.colorSwatches.take(4).forEach { color ->
                                Box(
                                    modifier = Modifier
                                        .size(14.dp)
                                        .clip(CircleShape)
                                        .background(Color(color))
                                        .border(0.5.dp, Color(0xFFCCCCCC), CircleShape)
                                )
                            }
                        }
                    }

                    // Sales tag
                    if (card.salesTag.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = card.salesTag,
                            fontSize = 12.sp,
                            color = Color(0xFF888888)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Add to Cart button
            Button(
                onClick = onAddToCartClick,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AmazonCheckoutYellow),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text(
                    text = "Add to Cart",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}
