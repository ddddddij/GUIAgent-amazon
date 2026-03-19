package com.example.amazon_sim.ui.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.ui.screen.home.DealProduct
import com.example.amazon_sim.ui.theme.AmazonDealRed
import com.example.amazon_sim.ui.theme.AmazonLinkBlue
import com.example.amazon_sim.ui.theme.AmazonTextBlack

@Composable
fun HomeDevicesGrid(
    title: String,
    devices: List<DealProduct>,
    onProductClick: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = AmazonTextBlack,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val rows = devices.chunked(2)
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { device ->
                    DeviceGridCard(
                        modifier = Modifier.weight(1f),
                        device = device,
                        onClick = { onProductClick(device.id) }
                    )
                }
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Text(
            text = "See more",
            color = AmazonLinkBlue,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
private fun DeviceGridCard(
    modifier: Modifier = Modifier,
    device: DealProduct,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(bottom = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(device.placeholderColor),
            contentAlignment = Alignment.BottomStart
        ) {
            Box(
                modifier = Modifier
                    .background(AmazonDealRed, RoundedCornerShape(topEnd = 4.dp))
                    .padding(horizontal = 6.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "${device.discountPercent}% off",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Limited time deal",
            color = AmazonDealRed,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}