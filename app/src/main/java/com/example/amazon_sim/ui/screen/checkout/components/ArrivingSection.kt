package com.example.amazon_sim.ui.screen.checkout.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.domain.model.CheckoutItem

@Composable
fun ArrivingSection(
    items: List<CheckoutItem>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        val deliveryDate = items.firstOrNull()?.freeDeliveryDate ?: ""
        if (deliveryDate.isNotBlank()) {
            Text(
                text = "Arriving $deliveryDate",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF007600)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        items.forEach { item ->
            CheckoutItemRow(item = item)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
