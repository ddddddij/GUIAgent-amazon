package com.example.amazon_sim.ui.screen.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.domain.model.Address
import com.example.amazon_sim.domain.model.CheckoutItem
import com.example.amazon_sim.domain.model.PaymentMethod
import com.example.amazon_sim.ui.screen.checkout.components.ArrivingSection
import com.example.amazon_sim.ui.screen.checkout.components.DeliveryAddressSection
import com.example.amazon_sim.ui.screen.checkout.components.OrderSummarySection
import com.example.amazon_sim.ui.screen.checkout.components.PaymentSuccessDialog
import com.example.amazon_sim.ui.theme.AmazonCheckoutYellow
import com.example.amazon_sim.ui.theme.AmazonProfileTopBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    items: List<CheckoutItem>,
    summary: OrderSummary,
    deliveryAddress: Address?,
    selectedPaymentMethod: PaymentMethod?,
    showSuccessDialog: Boolean,
    onBackClick: () -> Unit,
    onChangeAddress: () -> Unit,
    onPlaceOrder: () -> Unit,
    onViewOrders: () -> Unit,
    onContinueShopping: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Review your order",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AmazonProfileTopBackground
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                // Order summary
                OrderSummarySection(summary = summary)

                HorizontalDivider(color = Color(0xFFEEEEEE))

                // Payment method
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text(
                        text = "Payment method",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = when (selectedPaymentMethod) {
                            PaymentMethod.CREDIT_DEBIT_CARD -> "Credit or debit card"
                            PaymentMethod.AMAZON_GIFT_CARD -> "Amazon Gift Card"
                            else -> "Not selected"
                        },
                        fontSize = 14.sp,
                        color = Color(0xFF333333)
                    )
                }

                HorizontalDivider(color = Color(0xFFEEEEEE))

                // Delivery address
                DeliveryAddressSection(
                    address = deliveryAddress,
                    onChangeAddress = onChangeAddress
                )

                HorizontalDivider(color = Color(0xFFEEEEEE))

                // Arriving section with items
                ArrivingSection(items = items)

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Bottom Place Order button
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5))
                    .padding(16.dp)
            ) {
                Button(
                    onClick = onPlaceOrder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AmazonCheckoutYellow,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Place your order",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }

    if (showSuccessDialog) {
        PaymentSuccessDialog(
            onViewOrders = onViewOrders,
            onContinueShopping = onContinueShopping
        )
    }
}
