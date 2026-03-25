package com.example.amazon_sim.ui.screen.order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.domain.model.Order
import com.example.amazon_sim.domain.model.OrderItem
import com.example.amazon_sim.domain.model.OrderStatus
import com.example.amazon_sim.ui.screen.home.components.HomeProductAssetImage
import com.example.amazon_sim.ui.theme.AmazonBuyNowOrange
import com.example.amazon_sim.ui.theme.AmazonCartLinkBlue
import com.example.amazon_sim.ui.theme.AmazonCheckoutYellow
import com.example.amazon_sim.ui.theme.AmazonDividerGray
import com.example.amazon_sim.ui.theme.AmazonProfileTopBackground
import com.example.amazon_sim.ui.theme.AmazonSearchBarBorder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrderDetailScreen(
    order: Order,
    onBackClick: () -> Unit,
    onCancelOrder: () -> Unit,
    onConfirmReceipt: () -> Unit,
    onBuyAgainClick: (OrderItem) -> Unit = {}
) {
    var showCancelDialog by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .statusBarsPadding()
    ) {
        // Top bar - same pattern as OrderScreen
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AmazonProfileTopBackground)
                .padding(horizontal = 4.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .border(1.dp, AmazonSearchBarBorder, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Search Amazon",
                    fontSize = 14.sp,
                    color = Color(0xFF999999)
                )
            }
            IconButton(onClick = { }) {
                Icon(Icons.Filled.CameraAlt, contentDescription = "Camera", tint = Color.Gray, modifier = Modifier.size(24.dp))
            }
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Mic, contentDescription = "Voice", tint = Color.Gray, modifier = Modifier.size(24.dp))
            }
        }

        // Scrollable content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Page title
            Text(
                text = "Order Details",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Order status card
            OrderStatusCard(order = order)

            Spacer(modifier = Modifier.height(12.dp))

            // Order items card
            OrderItemsCard(items = order.items, onBuyAgainClick = onBuyAgainClick)

            Spacer(modifier = Modifier.height(12.dp))

            // Order info card
            OrderInfoCard(order = order)

            Spacer(modifier = Modifier.height(12.dp))

            // Shipping address card
            ShippingAddressCard(order = order)

            Spacer(modifier = Modifier.height(16.dp))

            // Action buttons
            ActionButtonsArea(
                orderStatus = order.orderStatus,
                onCancelClick = { showCancelDialog = true },
                onConfirmReceiptClick = { showConfirmDialog = true }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Cancel confirmation dialog
    if (showCancelDialog) {
        ConfirmActionDialog(
            title = "Cancel Order",
            message = "Are you sure you want to cancel this order? This action cannot be undone.",
            confirmText = "Cancel Order",
            onConfirm = {
                showCancelDialog = false
                onCancelOrder()
            },
            onDismiss = { showCancelDialog = false }
        )
    }

    // Confirm receipt dialog
    if (showConfirmDialog) {
        ConfirmActionDialog(
            title = "Confirm Receipt",
            message = "Have you received all items in this order?",
            confirmText = "Confirm",
            onConfirm = {
                showConfirmDialog = false
                onConfirmReceipt()
            },
            onDismiss = { showConfirmDialog = false }
        )
    }
}

// ─── Order Status Card ──────────────────────────────────────────────────────────

@Composable
private fun OrderStatusCard(order: Order) {
    val (statusText, statusColor) = getStatusDisplay(order.orderStatus)
    val (subText, _) = getStatusSubDisplay(order.orderStatus)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = statusText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = statusColor
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subText,
            fontSize = 14.sp,
            color = Color(0xFF666666)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Status progress indicator
        StatusProgressBar(orderStatus = order.orderStatus)
    }
}

@Composable
private fun StatusProgressBar(orderStatus: OrderStatus) {
    if (orderStatus == OrderStatus.CANCELED) return

    val steps = listOf("Ordered", "Shipped", "Delivered")
    val currentStep = when (orderStatus) {
        OrderStatus.PENDING -> 0
        OrderStatus.UNSHIPPED -> 0
        OrderStatus.SHIPPED -> 1
        OrderStatus.DELIVERED -> 2
        OrderStatus.CANCELED -> -1
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, label ->
            val isCompleted = index <= currentStep
            val dotColor = if (isCompleted) Color(0xFF007600) else Color(0xFFCCCCCC)

            // Dot
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(dotColor)
            )

            if (index < steps.lastIndex) {
                // Connecting line
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(3.dp)
                        .background(
                            if (index < currentStep) Color(0xFF007600) else Color(0xFFCCCCCC)
                        )
                )
            }
        }
    }

    // Labels under dots
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        steps.forEach { label ->
            Text(
                text = label,
                fontSize = 11.sp,
                color = Color(0xFF666666)
            )
        }
    }
}

// ─── Order Items Card ───────────────────────────────────────────────────────────

@Composable
private fun OrderItemsCard(items: List<OrderItem>, onBuyAgainClick: (OrderItem) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Items Ordered",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(12.dp))

        items.forEachIndexed { index, item ->
            OrderItemRow(item = item, onBuyAgainClick = { onBuyAgainClick(item) })
            if (index < items.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 10.dp),
                    color = AmazonDividerGray
                )
            }
        }
    }
}

@Composable
private fun OrderItemRow(item: OrderItem, onBuyAgainClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product image
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFF5F5F5))
            ) {
                HomeProductAssetImage(
                    assetPath = item.productImage,
                    contentDescription = item.productName,
                    fallbackColor = Color(0xFFCCCCCC)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.productName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Specs if any
                if (item.selectedSpec.isNotEmpty()) {
                    val specText = item.selectedSpec.joinToString(" | ") { "${it.specType}: ${it.specValue}" }
                    Text(
                        text = specText,
                        fontSize = 12.sp,
                        color = Color(0xFF888888),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$${String.format("%.2f", item.price)}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Qty: ${item.quantity}",
                        fontSize = 13.sp,
                        color = Color(0xFF666666)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Buy Again button
        Button(
            onClick = onBuyAgainClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AmazonCheckoutYellow)
        ) {
            Text(
                text = "Buy Again",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}

// ─── Order Info Card ────────────────────────────────────────────────────────────

@Composable
private fun OrderInfoCard(order: Order) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy  HH:mm", Locale.US)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Order Information",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(12.dp))

        InfoRow(label = "Order ID", value = order.orderId)
        InfoRow(label = "Order Date", value = dateFormat.format(Date(order.createdAt)))
        InfoRow(label = "Payment", value = order.paymentMethod)
        InfoRow(label = "Total", value = "$${String.format("%.2f", order.totalAmount)}", isBold = true)
    }
}

@Composable
private fun InfoRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = Color(0xFF666666)
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            color = Color.Black
        )
    }
}

// ─── Shipping Address Card ──────────────────────────────────────────────────────

@Composable
private fun ShippingAddressCard(order: Order) {
    val addr = order.shippingAddress

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Shipping Address",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = addr.fullName, fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Medium)
        Text(text = addr.streetAddress, fontSize = 13.sp, color = Color(0xFF333333))
        if (addr.aptSuite.isNotBlank()) {
            Text(text = addr.aptSuite, fontSize = 13.sp, color = Color(0xFF333333))
        }
        Text(
            text = "${addr.city}, ${addr.state} ${addr.zipCode}",
            fontSize = 13.sp,
            color = Color(0xFF333333)
        )
        Text(text = addr.country, fontSize = 13.sp, color = Color(0xFF333333))
        Text(text = addr.phoneNumber, fontSize = 13.sp, color = Color(0xFF666666))
    }
}

// ─── Action Buttons ─────────────────────────────────────────────────────────────

@Composable
private fun ActionButtonsArea(
    orderStatus: OrderStatus,
    onCancelClick: () -> Unit,
    onConfirmReceiptClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        when (orderStatus) {
            OrderStatus.PENDING, OrderStatus.UNSHIPPED -> {
                // Can cancel
                Button(
                    onClick = onCancelClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AmazonCheckoutYellow)
                ) {
                    Text(
                        text = "Cancel Order",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
            }
            OrderStatus.SHIPPED -> {
                // Can confirm receipt
                Button(
                    onClick = onConfirmReceiptClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AmazonCheckoutYellow)
                ) {
                    Text(
                        text = "Confirm Receipt",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
            }
            OrderStatus.DELIVERED, OrderStatus.CANCELED -> {
                // No actionable buttons
            }
        }
    }
}

// ─── Confirm Dialog ─────────────────────────────────────────────────────────────

@Composable
private fun ConfirmActionDialog(
    title: String,
    message: String,
    confirmText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = title, fontWeight = FontWeight.Bold)
        },
        text = {
            Text(text = message, fontSize = 14.sp, color = Color(0xFF333333))
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = AmazonCheckoutYellow)
            ) {
                Text(text = confirmText, color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Go Back", color = AmazonCartLinkBlue)
            }
        }
    )
}

// ─── Helpers (reused from OrderCard) ────────────────────────────────────────────

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
