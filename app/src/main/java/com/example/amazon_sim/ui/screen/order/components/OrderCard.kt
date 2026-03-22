package com.example.amazon_sim.ui.screen.order.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.amazon_sim.domain.model.Order
import com.example.amazon_sim.domain.model.OrderStatus
import com.example.amazon_sim.ui.screen.home.components.HomeProductAssetImage

@Composable
fun OrderCard(
    order: Order,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val firstItem = order.items.firstOrNull() ?: return
    val (statusText, statusColor) = getStatusDisplay(order.orderStatus)
    val (subText, subColor) = getStatusSubDisplay(order.orderStatus)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFDDDDDD), RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Product image
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFF5F5F5))
        ) {
            HomeProductAssetImage(
                assetPath = firstItem.productImage,
                contentDescription = firstItem.productName,
                fallbackColor = Color(0xFFCCCCCC)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Status info
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = statusText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = statusColor
            )
            Text(
                text = subText,
                fontSize = 13.sp,
                color = subColor,
                maxLines = 2
            )
        }
    }
}

private fun getStatusDisplay(status: OrderStatus): Pair<String, Color> = when (status) {
    OrderStatus.PENDING -> "Pending" to Color(0xFFFF9900)
    OrderStatus.UNSHIPPED -> "Unshipped" to Color(0xFF0066C0)
    OrderStatus.SHIPPED -> "Shipped" to Color(0xFF007600)
    OrderStatus.DELIVERED -> "Delivered" to Color(0xFF007600)
    OrderStatus.CANCELED -> "Cancelled" to Color(0xFF333333)
}

private fun getStatusSubDisplay(status: OrderStatus): Pair<String, Color> = when (status) {
    OrderStatus.PENDING -> "Waiting for payment confirmation" to Color(0xFF888888)
    OrderStatus.UNSHIPPED -> "Your order is being prepared" to Color(0xFF888888)
    OrderStatus.SHIPPED -> "Your order is on the way" to Color(0xFF888888)
    OrderStatus.DELIVERED -> "Your order has been delivered" to Color(0xFF888888)
    OrderStatus.CANCELED -> "Your order was cancelled. You have not been charged for this order." to Color(0xFF555555)
}
