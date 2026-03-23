package com.example.amazon_sim.ui.screen.checkout.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.ui.screen.checkout.OrderSummary
import java.util.Locale

@Composable
fun OrderSummarySection(
    summary: OrderSummary,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = "Order Summary",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        SummaryRow("Items:", summary.itemsTotal)
        SummaryRow("Shipping & handling:", summary.shipping)
        SummaryRow("Estimated tax:", summary.tax)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Order total:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$${String.format(Locale.US, "%.2f", summary.orderTotal)}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = androidx.compose.ui.graphics.Color(0xFFB12704)
            )
        }
    }
}

@Composable
private fun SummaryRow(label: String, amount: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 14.sp, color = androidx.compose.ui.graphics.Color(0xFF333333))
        Text(
            text = if (amount == 0.0) "FREE" else "$${String.format(Locale.US, "%.2f", amount)}",
            fontSize = 14.sp,
            color = if (amount == 0.0) androidx.compose.ui.graphics.Color(0xFF007600)
                    else androidx.compose.ui.graphics.Color(0xFF333333)
        )
    }
}
