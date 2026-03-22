package com.example.amazon_sim.ui.screen.cart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.ui.screen.cart.CartItemUi
import com.example.amazon_sim.ui.screen.cart.StockStatusType
import com.example.amazon_sim.ui.screen.home.components.HomeProductAssetImage
import com.example.amazon_sim.ui.theme.AmazonCartLinkBlue
import com.example.amazon_sim.ui.theme.AmazonDealRed
import com.example.amazon_sim.ui.theme.AmazonInStockGreen
import com.example.amazon_sim.ui.theme.AmazonTextBlack
import com.example.amazon_sim.ui.theme.AmazonTextDark
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CartItemCard(
    item: CartItemUi,
    onToggleSelect: () -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onDelete: () -> Unit,
    onSaveForLater: () -> Unit,
    onCompare: () -> Unit = {},
    onShare: () -> Unit = {},
    onViewInRoom: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Top row: checkbox + image + info
        Row(modifier = Modifier.fillMaxWidth()) {
            Checkbox(
                checked = item.isSelected,
                onCheckedChange = { onToggleSelect() },
                colors = CheckboxDefaults.colors(
                    checkedColor = AmazonCartLinkBlue,
                    uncheckedColor = Color.Gray
                )
            )
            // Product image
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (item.imageAssetPath.isNotBlank()) {
                    HomeProductAssetImage(
                        assetPath = item.imageAssetPath,
                        contentDescription = item.productName,
                        fallbackColor = Color(item.placeholderColor)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color(item.placeholderColor)),
                        contentAlignment = Alignment.Center
                    ) {}
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            // Product info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.productName,
                    fontSize = 14.sp,
                    color = AmazonTextBlack,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Price
                Text(
                    buildAnnotatedString {
                        withStyle(SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)) {
                            append("$")
                        }
                        val parts = String.format(Locale.US, "%.2f", item.price).split(".")
                        withStyle(SpanStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)) {
                            append(parts[0])
                        }
                        withStyle(SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)) {
                            append(parts[1])
                        }
                    },
                    color = AmazonTextBlack
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Stock status
                val statusColor = when (item.stockStatusType) {
                    StockStatusType.IN_STOCK -> AmazonInStockGreen
                    StockStatusType.LOW_STOCK -> AmazonDealRed
                    StockStatusType.FREE_RETURN -> AmazonCartLinkBlue
                }
                Text(
                    text = item.stockStatus,
                    fontSize = 12.sp,
                    color = statusColor
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Quantity selector + Delete + Save for later
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            QuantitySelector(
                quantity = item.quantity,
                onDecrease = onDecrease,
                onIncrease = onIncrease
            )
            OutlinedButton(onClick = onDelete, shape = RoundedCornerShape(20.dp)) {
                Text("Delete", color = AmazonTextDark, fontSize = 13.sp)
            }
            OutlinedButton(onClick = onSaveForLater, shape = RoundedCornerShape(20.dp)) {
                Text("Save for later", color = AmazonTextDark, fontSize = 13.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Compare + Share row
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(onClick = onCompare, shape = RoundedCornerShape(20.dp)) {
                Text("\uD83D\uDD36 Compare with similar items", color = AmazonTextDark, fontSize = 13.sp)
            }
            OutlinedButton(onClick = onShare, shape = RoundedCornerShape(20.dp)) {
                Text("Share", color = AmazonTextDark, fontSize = 13.sp)
            }
        }

        // View in your room (optional)
        if (item.showViewInRoom) {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(onClick = onViewInRoom, shape = RoundedCornerShape(20.dp)) {
                Text("View in your room", color = AmazonTextDark, fontSize = 13.sp)
            }
        }
    }
}