package com.example.amazon_sim.ui.screen.checkout.components

import androidx.compose.foundation.clickable
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
import com.example.amazon_sim.domain.model.Address

@Composable
fun DeliveryAddressSection(
    address: Address?,
    onChangeAddress: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = "Delivery address",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        if (address != null) {
            Text(
                text = address.fullName,
                fontSize = 14.sp,
                color = Color.Black
            )
            Text(
                text = buildString {
                    append(address.streetAddress)
                    if (address.aptSuite.isNotBlank()) append(", ${address.aptSuite}")
                },
                fontSize = 14.sp,
                color = Color(0xFF333333)
            )
            Text(
                text = "${address.city}, ${address.state} ${address.zipCode}",
                fontSize = 14.sp,
                color = Color(0xFF333333)
            )
            Text(
                text = address.country,
                fontSize = 14.sp,
                color = Color(0xFF333333)
            )
        } else {
            Text(
                text = "No delivery address set",
                fontSize = 14.sp,
                color = Color(0xFF666666)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Change",
            fontSize = 14.sp,
            color = Color(0xFF007185),
            modifier = Modifier.clickable(onClick = onChangeAddress)
        )
    }
}
