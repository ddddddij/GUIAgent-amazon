package com.example.amazon_sim.ui.screen.productdetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.ui.theme.AmazonCartLinkBlue
import com.example.amazon_sim.ui.theme.AmazonInStockGreen

@Composable
fun DeliverySection(
    freeDeliveryDate: String,
    fastestDeliveryDate: String,
    countdownText: String,
    deliverToText: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Free delivery
        Text(
            text = buildAnnotatedString {
                append("FREE delivery ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(freeDeliveryDate)
                }
            },
            fontSize = 14.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Fastest delivery
        Text(
            text = buildAnnotatedString {
                append("Or fastest delivery ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(fastestDeliveryDate)
                }
                append(". Order within ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = AmazonInStockGreen)) {
                    append(countdownText)
                }
            },
            fontSize = 14.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Deliver to
        Text(
            text = "\uD83D\uDCCD Deliver to $deliverToText",
            fontSize = 14.sp,
            color = AmazonCartLinkBlue
        )
    }
}
