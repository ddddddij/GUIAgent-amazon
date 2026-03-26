package com.example.amazon_sim.ui.screen.interests.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun InterestProductCard(
    product: Product,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onSeeOptionsClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
    ) {
        // Product image with heart icon
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color(0xFFF5F5F5), RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
        ) {
            HomeProductAssetImage(
                assetPath = product.imageUrl,
                contentDescription = product.name,
                fallbackColor = Color(0xFFF5F5F5)
            )

            // Heart icon at bottom-end
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
                    .size(28.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color(0xFF888888),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Product name
        Text(
            text = product.name,
            fontSize = 14.sp,
            color = Color(0xFF333333),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )

        // Rating row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = AmazonRatingOrange,
                modifier = Modifier.size(11.dp)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "${product.rating}",
                fontSize = 11.sp,
                color = AmazonRatingOrange
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "(${String.format("%,d", product.reviewCount)})",
                fontSize = 11.sp,
                color = Color(0xFF888888)
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        // Price row
        val priceText = buildAnnotatedString {
            withStyle(SpanStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold, baselineShift = BaselineShift.Superscript)) {
                append("$")
            }
            val parts = String.format("%.2f", product.price).split(".")
            withStyle(SpanStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold)) {
                append(parts[0])
            }
            withStyle(SpanStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold, baselineShift = BaselineShift.Superscript)) {
                append(".${parts[1]}")
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(text = priceText, color = Color.Black)
            if (product.typicalPrice > 0 && product.typicalPrice > product.price) {
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "List: $${String.format("%.2f", product.typicalPrice)}",
                    fontSize = 11.sp,
                    color = Color(0xFF888888),
                    textDecoration = TextDecoration.LineThrough
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // See options button
        OutlinedButton(
            onClick = onSeeOptionsClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, Color(0xFFCCCCCC))
        ) {
            Text("See options", fontSize = 13.sp, color = Color(0xFF333333))
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}
