package com.example.amazon_sim.ui.screen.buyagain.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
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
import com.example.amazon_sim.ui.screen.buyagain.TopSellerCardData
import com.example.amazon_sim.ui.screen.home.components.HomeProductAssetImage
import com.example.amazon_sim.ui.theme.AmazonDealRed
import com.example.amazon_sim.ui.theme.AmazonRatingOrange

@Composable
fun TopSellerCard(
    card: TopSellerCardData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
    ) {
        // Product image
        HomeProductAssetImage(
            assetPath = card.imageAssetPath,
            contentDescription = card.name,
            fallbackColor = Color(0xFFCCCCCC),
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(Color.White)
        )

        Column(modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)) {
            // Product name
            Text(
                text = card.name,
                fontSize = 13.sp,
                color = Color(0xFF333333),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            // Spec subtitle
            if (card.specSubtitle.isNotEmpty()) {
                Text(
                    text = card.specSubtitle,
                    fontSize = 11.sp,
                    color = Color(0xFF888888),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Rating row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = AmazonRatingOrange,
                    modifier = Modifier.size(11.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "${card.rating}",
                    fontSize = 11.sp,
                    color = AmazonRatingOrange
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "(${String.format("%,d", card.reviewCount)})",
                    fontSize = 11.sp,
                    color = Color(0xFF888888)
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Discount + price row
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (card.discountPercent > 0) {
                    Text(
                        text = "-${card.discountPercent}%",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = AmazonDealRed
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                val priceText = buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold, baselineShift = BaselineShift.Superscript)) {
                        append("$")
                    }
                    val parts = String.format("%.2f", card.price).split(".")
                    withStyle(SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)) {
                        append(parts[0])
                    }
                    withStyle(SpanStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold, baselineShift = BaselineShift.Superscript)) {
                        append(".${parts[1]}")
                    }
                }
                Text(text = priceText, color = Color.Black)
            }

            // Typical price (strikethrough)
            if (card.typicalPrice > card.price) {
                Text(
                    text = "Typical: $${String.format("%.2f", card.typicalPrice)}",
                    fontSize = 11.sp,
                    color = Color(0xFF888888),
                    textDecoration = TextDecoration.LineThrough
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Delivery date
            Text(
                text = buildAnnotatedString {
                    append("Delivery ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Mon, Mar 30")
                    }
                },
                fontSize = 11.sp,
                color = Color.Black
            )
        }
    }
}
