package com.example.amazon_sim.ui.screen.order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.domain.model.Order
import com.example.amazon_sim.domain.model.OrderStatus
import com.example.amazon_sim.ui.screen.order.components.FilterBottomSheet
import com.example.amazon_sim.ui.screen.order.components.OrderCard
import com.example.amazon_sim.ui.theme.AmazonProfileTopBackground
import com.example.amazon_sim.ui.theme.AmazonSearchBarBorder

@Composable
fun OrderScreen(
    orders: List<Order>,
    currentFilter: OrderStatus?,
    onFilterChange: (OrderStatus?) -> Unit,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit = {},
    onOrderClick: (Order) -> Unit = {}
) {
    var showFilterSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        // Top bar
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
                    .clickable(onClick = onSearchClick)
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

        // Page title
        Text(
            text = "Your Orders",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )

        // Filter row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showFilterSheet = true }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Filter",
                fontSize = 14.sp,
                color = Color.Black
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Filter",
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
        }

        HorizontalDivider(color = Color(0xFFEEEEEE))

        if (orders.isEmpty()) {
            // Empty state
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No orders found",
                    fontSize = 14.sp,
                    color = Color(0xFF888888)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp)
            ) {
                // Section header
                item {
                    Column(modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)) {
                        Text(
                            text = "Purchase history",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Past three months",
                            fontSize = 14.sp,
                            color = Color(0xFF888888)
                        )
                    }
                }

                // Order cards
                items(orders, key = { it.orderId }) { order ->
                    OrderCard(
                        order = order,
                        onClick = { onOrderClick(order) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // End of list text
                item {
                    Text(
                        text = "You've reached the end of Your Orders",
                        fontSize = 13.sp,
                        color = Color(0xFF888888),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    }

    // Filter bottom sheet
    if (showFilterSheet) {
        FilterBottomSheet(
            currentFilter = currentFilter,
            onApply = { status ->
                onFilterChange(status)
                showFilterSheet = false
            },
            onDismiss = { showFilterSheet = false }
        )
    }
}
