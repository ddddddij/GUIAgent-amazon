package com.example.amazon_sim.ui.screen.productdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.domain.model.ReviewTag
import com.example.amazon_sim.ui.theme.AmazonCartLinkBlue
import com.example.amazon_sim.ui.theme.AmazonInStockGreen
import com.example.amazon_sim.ui.theme.AmazonRatingOrange
import com.example.amazon_sim.ui.theme.AmazonSecondaryText

@Composable
fun CustomerReviewsSection(
    rating: Float,
    reviewCount: Int,
    globalRatingsCount: Int,
    reviewSummary: String,
    reviewTags: List<ReviewTag>,
    customerPhotoPlaceholderColors: List<Long>,
    onRatingClick: () -> Unit = {},
    onTagClick: (ReviewTag) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Section title
        Text(
            text = "Customer reviews",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Rating row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Stars
            repeat(5) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = AmazonRatingOrange,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$rating out of 5",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onRatingClick, modifier = Modifier.size(24.dp)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "More",
                    tint = Color(0xFF333333)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${String.format("%,d", globalRatingsCount)} global ratings",
            fontSize = 14.sp,
            color = AmazonSecondaryText
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Customers say
        Text(
            text = "Customers say",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = reviewSummary,
            fontSize = 14.sp,
            color = Color(0xFF333333),
            lineHeight = 21.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        // AI generated label
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "\uD83E\uDD16",
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "AI Generated from the text of customer reviews",
                fontSize = 12.sp,
                color = Color(0xFF888888)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Select to learn more
        Text(
            text = "Select to learn more",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Review tags - two column layout
        val tagPairs = reviewTags.chunked(2)
        tagPairs.forEach { pair ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                pair.forEach { tag ->
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = AmazonInStockGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = tag.label,
                            fontSize = 14.sp,
                            color = AmazonCartLinkBlue,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${tag.count}",
                            fontSize = 14.sp,
                            color = AmazonSecondaryText
                        )
                    }
                }
                // Fill empty space if odd number of tags
                if (pair.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Customer photos and videos
        Text(
            text = "Customer photos and videos",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            customerPhotoPlaceholderColors.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(color))
                )
            }
        }
    }
}
