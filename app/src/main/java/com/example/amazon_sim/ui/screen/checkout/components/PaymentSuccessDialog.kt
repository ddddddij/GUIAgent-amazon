package com.example.amazon_sim.ui.screen.checkout.components

import android.content.Context
import android.content.Intent
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.ui.theme.AmazonCheckoutYellow

@Composable
fun PaymentSuccessDialog(
    onViewOrders: () -> Unit,
    onContinueShopping: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(
                text = "Order placed successfully!",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Text(
                text = "Thank you for your purchase. Your order has been confirmed and will be shipped soon.",
                fontSize = 14.sp,
                color = Color(0xFF333333)
            )
        },
        confirmButton = {
            Button(
                onClick = onViewOrders,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AmazonCheckoutYellow,
                    contentColor = Color.Black
                )
            ) {
                Text("View Orders", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onContinueShopping) {
                Text("Continue Shopping", color = Color(0xFF007185))
            }
        }
    )
}
