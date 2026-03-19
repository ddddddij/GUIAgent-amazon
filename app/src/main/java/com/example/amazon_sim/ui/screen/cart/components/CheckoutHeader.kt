package com.example.amazon_sim.ui.screen.cart.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.amazon_sim.ui.theme.AmazonCheckoutYellow
import com.example.amazon_sim.ui.theme.AmazonTextBlack
import java.util.Locale

@Composable
fun CheckoutHeader(
    subtotal: Double,
    selectedCount: Int,
    onCheckout: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        if (selectedCount == 0) {
            Text(
                text = "No items selected",
                fontSize = 18.sp,
                color = AmazonTextBlack
            )
        } else {
            Text(
                buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = 18.sp)) { append("Subtotal ") }
                    withStyle(SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)) { append("$") }
                    withStyle(SpanStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)) {
                        val parts = String.format(Locale.US, "%.2f", subtotal).split(".")
                        append(parts[0])
                    }
                    withStyle(SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)) {
                        val parts = String.format(Locale.US, "%.2f", subtotal).split(".")
                        append(parts[1])
                    }
                },
                color = AmazonTextBlack
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onCheckout,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AmazonCheckoutYellow,
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Proceed to checkout ($selectedCount item${if (selectedCount != 1) "s" else ""})",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}
