package com.example.amazon_sim.ui.screen.lists.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.amazon_sim.domain.model.ProductDetailData
import com.example.amazon_sim.ui.screen.home.components.HomeProductAssetImage
import com.example.amazon_sim.ui.theme.AmazonRatingOrange

@Composable
fun ProductDetailCard(
    product: Product,
    detail: ProductDetailData?,
    onAddToCartClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    // Build variant-aware display name
    val displayName = if (detail != null) {
        val base = detail.baseName.ifEmpty { detail.name }
        val variantLabels = detail.specGroups
            .filter { it.options.size > 1 }
            .mapNotNull { group ->
                val defaultId = detail.defaultSpecOptionIds[group.dimensionName]
                group.options.find { it.id == defaultId }?.label
            }
        if (variantLabels.isNotEmpty()) "$base, ${variantLabels.joinToString(", ")}" else base
    } else {
        product.name
    }

    // Extract spec info from detail
    val defaultSpecs = detail?.let { d ->
        d.specGroups.mapNotNull { group ->
            val defaultId = d.defaultSpecOptionIds[group.dimensionName]
            val option = group.options.find { it.id == defaultId }
            if (option != null) {
                group.dimensionName to option.label
            } else null
        }
    } ?: emptyList()

    // Get price from detail spec options
    val priceOption = detail?.specGroups
        ?.lastOrNull { group ->
            val defaultId = detail.defaultSpecOptionIds[group.dimensionName]
            group.options.find { it.id == defaultId }?.price != null
        }?.let { group ->
            val defaultId = detail.defaultSpecOptionIds[group.dimensionName]
            group.options.find { it.id == defaultId }
        }

    val displayPrice = priceOption?.price ?: product.price
    val originalPrice = priceOption?.originalPrice
    val deliveryDate = detail?.freeDeliveryDate ?: "Friday, March 27"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // Product image
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color(0xFFF5F5F5), RoundedCornerShape(4.dp))
                    .clip(RoundedCornerShape(4.dp))
            ) {
                HomeProductAssetImage(
                    assetPath = product.imageUrl,
                    contentDescription = product.name,
                    fallbackColor = Color(0xFFCCCCCC)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Product info
            Column(modifier = Modifier.weight(1f)) {
                // Name
                Text(
                    text = displayName,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Brand and type
                Text(
                    text = "by ${product.brandName} (${product.category})",
                    fontSize = 13.sp,
                    color = Color(0xFF888888)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Rating
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = AmazonRatingOrange,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("${product.rating}", fontSize = 12.sp, color = AmazonRatingOrange)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "(${String.format("%,d", product.reviewCount)})",
                        fontSize = 12.sp,
                        color = Color(0xFF888888)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Spec info rows
                defaultSpecs.forEach { (dim, label) ->
                    Text(
                        text = "$dim : $label",
                        fontSize = 13.sp,
                        color = Color(0xFF888888)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Price row
                Row(verticalAlignment = Alignment.Bottom) {
                    val priceText = buildAnnotatedString {
                        withStyle(SpanStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold, baselineShift = BaselineShift.Superscript)) {
                            append("$")
                        }
                        val parts = String.format("%.2f", displayPrice).split(".")
                        withStyle(SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)) {
                            append(parts[0])
                        }
                        withStyle(SpanStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold, baselineShift = BaselineShift.Superscript)) {
                            append(".${parts[1]}")
                        }
                    }
                    Text(text = priceText, color = Color.Black)

                    if (originalPrice != null && originalPrice > displayPrice) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "List: $${String.format("%.2f", originalPrice)}",
                            fontSize = 13.sp,
                            color = Color(0xFF888888),
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                // Delivery
                Text(
                    text = buildAnnotatedString {
                        append("FREE delivery ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(deliveryDate)
                        }
                    },
                    fontSize = 13.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Action row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onAddToCartClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD814))
                    ) {
                        Text(
                            text = "Add to Cart",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = onMoreClick,
                        modifier = Modifier
                            .size(32.dp)
                            .border(1.dp, Color(0xFFCCCCCC), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreHoriz,
                            contentDescription = "More",
                            tint = Color(0xFF555555),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}
