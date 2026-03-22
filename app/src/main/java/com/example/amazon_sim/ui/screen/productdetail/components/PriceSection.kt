package com.example.amazon_sim.ui.screen.productdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.ui.theme.AmazonCartLinkBlue
import com.example.amazon_sim.ui.theme.AmazonDealRed
import com.example.amazon_sim.ui.theme.AmazonInStockGreen

@Composable
fun PriceSection(
    currentPrice: Double,
    originalPrice: Double,
    discountPercent: Int,
    installmentMonthly: Double?,
    installmentMonths: Int?,
    onPriceHistoryClick: () -> Unit = {},
    onInstallmentClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Discount + Price row
        Row(verticalAlignment = Alignment.Bottom) {
            if (discountPercent > 0) {
                Text(
                    text = "-${discountPercent}%",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AmazonDealRed
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            // Price with superscript dollar sign
            val priceText = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        baselineShift = BaselineShift.Superscript
                    )
                ) {
                    append("$")
                }
                withStyle(
                    SpanStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    val parts = String.format("%.2f", currentPrice).split(".")
                    append(parts[0])
                }
                withStyle(
                    SpanStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        baselineShift = BaselineShift.Superscript
                    )
                ) {
                    val parts = String.format("%.2f", currentPrice).split(".")
                    append(".${parts[1]}")
                }
            }
            Text(text = priceText, color = Color.Black)

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Price history",
                fontSize = 14.sp,
                color = AmazonCartLinkBlue,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // List price
        if (originalPrice > currentPrice) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "List Price: ",
                    fontSize = 14.sp,
                    color = Color(0xFF888888)
                )
                Text(
                    text = "$${String.format("%.2f", originalPrice)}",
                    fontSize = 14.sp,
                    color = Color(0xFF888888),
                    textDecoration = TextDecoration.LineThrough
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Info",
                    tint = Color(0xFF888888),
                    modifier = Modifier.height(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Installment plan
        if (installmentMonthly != null && installmentMonths != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8F5E9), RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Or ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("$${String.format("%.2f", installmentMonthly)}/mo")
                        }
                        append(" ($installmentMonths mo). ")
                        withStyle(SpanStyle(color = AmazonCartLinkBlue)) {
                            append("Select from 2 plans")
                        }
                    },
                    fontSize = 14.sp,
                    color = AmazonInStockGreen
                )
            }
        }
    }
}
