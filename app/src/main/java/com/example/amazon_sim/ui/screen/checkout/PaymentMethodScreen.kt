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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.domain.model.PaymentMethod
import com.example.amazon_sim.ui.screen.checkout.components.GiftCodeToggleRow
import com.example.amazon_sim.ui.screen.checkout.components.PaymentOptionCard
import com.example.amazon_sim.ui.theme.AmazonCheckoutYellow
import com.example.amazon_sim.ui.theme.AmazonProfileTopBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentMethodScreen(
    selectedPaymentMethod: PaymentMethod?,
    onSelectPaymentMethod: (PaymentMethod) -> Unit,
    onContinue: () -> Unit,
    onCancel: () -> Unit
) {
    var giftCodeEnabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Select a payment method",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    TextButton(onClick = onCancel) {
                        Text(
                            text = "Cancel",
                            fontSize = 16.sp,
                            color = Color.Black
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
                    .padding(16.dp)
            ) {
                // Top Continue button
                Button(
                    onClick = onContinue,
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
                        text = "Continue",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Paying with",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Add new",
                    fontSize = 14.sp,
                    color = Color(0xFF007185)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Credit/Debit Card option
                PaymentOptionCard(
                    icon = "\uD83D\uDCB3",
                    iconBackgroundColor = Color(0xFF232F3E),
                    title = "Credit or debit card",
                    subtitle = "Visa, Mastercard, Amex...",
                    isSelected = selectedPaymentMethod == PaymentMethod.CREDIT_DEBIT_CARD,
                    onClick = { onSelectPaymentMethod(PaymentMethod.CREDIT_DEBIT_CARD) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Amazon Gift Card option
                PaymentOptionCard(
                    icon = "\uD83C\uDF81",
                    iconBackgroundColor = Color(0xFFFF9900),
                    title = "Amazon Gift Card",
                    subtitle = "",
                    isSelected = selectedPaymentMethod == PaymentMethod.AMAZON_GIFT_CARD,
                    onClick = { onSelectPaymentMethod(PaymentMethod.AMAZON_GIFT_CARD) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider(color = Color(0xFFEEEEEE))

                Spacer(modifier = Modifier.height(8.dp))

                // Gift code toggle
                GiftCodeToggleRow(
                    label = "Use a gift code, voucher, or promo code",
                    checked = giftCodeEnabled,
                    onCheckedChange = { giftCodeEnabled = it }
                )
            }

            // Bottom Continue button
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5))
                    .padding(16.dp)
            ) {
                Button(
                    onClick = onContinue,
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
                        text = "Continue",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}
