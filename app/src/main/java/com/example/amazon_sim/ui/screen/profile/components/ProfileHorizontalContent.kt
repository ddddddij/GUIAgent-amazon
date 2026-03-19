package com.example.amazon_sim.ui.screen.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.ui.screen.profile.ProfileAccountEntry
import com.example.amazon_sim.ui.screen.profile.ProfileBannerItem
import com.example.amazon_sim.ui.screen.profile.ProfileHighlightItem
import com.example.amazon_sim.ui.screen.profile.ProfileProductItem
import com.example.amazon_sim.ui.theme.AmazonBackground
import com.example.amazon_sim.ui.theme.AmazonCardBackground
import com.example.amazon_sim.ui.theme.AmazonDealYellow
import com.example.amazon_sim.ui.theme.AmazonSearchBarBorder
import com.example.amazon_sim.ui.theme.AmazonSecondaryText
import com.example.amazon_sim.ui.theme.AmazonTextBlack
import com.example.amazon_sim.ui.theme.AmazonTextDark

@Composable
fun ProfileHorizontalBanners(
    banners: List<ProfileBannerItem>,
    onBannerClick: (String) -> Unit = {},
    onButtonClick: (String) -> Unit = {}
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(banners) { banner ->
            Card(
                modifier = Modifier
                    .width(280.dp)
                    .clickable { onBannerClick(banner.id) },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = banner.backgroundColor)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White.copy(alpha = 0.18f))
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = banner.title,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = banner.subtitle,
                        color = Color.White.copy(alpha = 0.92f),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(AmazonDealYellow)
                            .clickable { onButtonClick(banner.buttonText) }
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = banner.buttonText,
                            color = AmazonTextBlack,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileHorizontalProducts(
    products: List<ProfileProductItem>,
    onProductClick: (String) -> Unit = {}
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(products) { product ->
            Card(
                modifier = Modifier
                    .width(160.dp)
                    .clickable { onProductClick(product.id) },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = AmazonBackground)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(132.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(product.placeholderColor)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = product.name,
                        color = AmazonTextBlack,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = product.detailLabel,
                        color = AmazonSecondaryText,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = product.priceLabel,
                        color = AmazonTextBlack,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileHorizontalHighlights(
    highlights: List<ProfileHighlightItem>,
    onHighlightClick: (String) -> Unit = {}
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(highlights) { item ->
            Card(
                modifier = Modifier
                    .width(220.dp)
                    .clickable { onHighlightClick(item.id) },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = item.backgroundColor)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    item.badgeText?.let { badge ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(AmazonBackground)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = badge,
                                color = AmazonTextDark,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    Text(
                        text = item.title,
                        color = AmazonTextBlack,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = item.subtitle,
                        color = AmazonTextDark,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileHorizontalAccountEntries(
    entries: List<ProfileAccountEntry>,
    onEntryClick: (String) -> Unit = {}
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(entries) { entry ->
            Card(
                modifier = Modifier
                    .width(160.dp)
                    .clickable { onEntryClick(entry.id) },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = AmazonBackground),
                border = androidx.compose.foundation.BorderStroke(1.dp, AmazonSearchBarBorder)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(88.dp)
                        .background(AmazonCardBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = entry.title,
                        color = AmazonTextBlack,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        }
    }
}
