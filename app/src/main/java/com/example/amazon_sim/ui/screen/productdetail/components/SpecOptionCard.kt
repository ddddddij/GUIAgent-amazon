package com.example.amazon_sim.ui.screen.productdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.domain.model.SpecOption
import com.example.amazon_sim.ui.theme.AmazonInStockGreen

@Composable
fun SpecOptionCard(
    option: SpecOption,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isSelected) Color(0xFF0066C0) else Color(0xFFCCCCCC)
    val borderWidth = if (isSelected) 2.dp else 1.dp
    val shape = RoundedCornerShape(8.dp)

    if (option.placeholderColor != null) {
        // Image card mode (e.g. color variants)
        Column(
            modifier = modifier
                .width(130.dp)
                .clip(shape)
                .border(borderWidth, borderColor, shape)
                .clickable(onClick = onClick)
                .background(Color.White)
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color(option.placeholderColor))
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = option.label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (option.price != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "$${String.format("%.2f", option.price)}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            if (option.originalPrice != null) {
                Text(
                    text = "$${String.format("%.2f", option.originalPrice)}",
                    fontSize = 12.sp,
                    color = Color(0xFF888888),
                    textDecoration = TextDecoration.LineThrough
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = option.stockStatus,
                fontSize = 12.sp,
                color = if (option.isInStock) AmazonInStockGreen else Color.Red
            )
        }
    } else {
        // Text-only mode (e.g. storage, configuration)
        Box(
            modifier = modifier
                .clip(shape)
                .border(borderWidth, borderColor, shape)
                .clickable(onClick = onClick)
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = option.label,
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (option.price != null) {
                    Text(
                        text = "$${String.format("%.2f", option.price)}",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
