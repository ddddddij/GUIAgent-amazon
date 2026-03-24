package com.example.amazon_sim.ui.screen.lists.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.MoreVert
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
import com.example.amazon_sim.ui.screen.home.components.HomeProductAssetImage
import com.example.amazon_sim.ui.theme.AmazonRatingOrange

@Composable
fun SavedProductCard(
    product: Product,
    originalPrice: Double?,
    deliveryDate: String,
    savedInListName: String,
    onAddToCartClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
    ) {
        // Product image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(Color(0xFFF5F5F5), RoundedCornerShape(4.dp))
                .clip(RoundedCornerShape(4.dp))
        ) {
            HomeProductAssetImage(
                assetPath = product.imageUrl,
                contentDescription = product.name,
                fallbackColor = Color(0xFFCCCCCC)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Product name
        Text(
            text = product.name,
            fontSize = 14.sp,
            color = Color(0xFF333333),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

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

        Spacer(modifier = Modifier.height(4.dp))

        // Price
        val priceText = buildAnnotatedString {
            withStyle(SpanStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold, baselineShift = BaselineShift.Superscript)) {
                append("$")
            }
            val parts = String.format("%.2f", product.price).split(".")
            withStyle(SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)) {
                append(parts[0])
            }
            withStyle(SpanStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold, baselineShift = BaselineShift.Superscript)) {
                append(".${parts[1]}")
            }
        }
        Text(text = priceText, color = Color.Black)

        // Original price
        if (originalPrice != null && originalPrice > product.price) {
            Text(
                text = "List: $${String.format("%.2f", originalPrice)}",
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

        Spacer(modifier = Modifier.height(2.dp))

        // Saved in
        Text(
            text = "Saved in $savedInListName",
            fontSize = 12.sp,
            color = Color(0xFF0066C0)
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
                    .height(36.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD814))
            ) {
                Text(
                    text = "Add to Cart",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = onMoreClick,
                modifier = Modifier
                    .size(36.dp)
                    .border(1.dp, Color(0xFFCCCCCC), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More",
                    tint = Color(0xFF555555),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
