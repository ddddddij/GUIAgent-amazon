package com.example.amazon_sim.ui.screen.cart.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.ui.theme.AmazonCheckoutYellow

@Composable
fun QuantitySelector(
    quantity: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit
) {
    Row(
        modifier = Modifier
            .border(2.dp, AmazonCheckoutYellow, RoundedCornerShape(24.dp))
            .height(36.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Filled.Delete,
            contentDescription = "Decrease",
            tint = Color.DarkGray,
            modifier = Modifier
                .clickable { onDecrease() }
                .padding(horizontal = 12.dp, vertical = 6.dp)
        )
        Text(
            text = "$quantity",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier.width(28.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Icon(
            Icons.Filled.Add,
            contentDescription = "Increase",
            tint = Color.DarkGray,
            modifier = Modifier
                .clickable { onIncrease() }
                .padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}
