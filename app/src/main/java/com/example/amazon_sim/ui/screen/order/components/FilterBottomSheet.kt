package com.example.amazon_sim.ui.screen.order.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.domain.model.OrderStatus
import com.example.amazon_sim.ui.theme.AmazonCartLinkBlue
import com.example.amazon_sim.ui.theme.AmazonCheckoutYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    currentFilter: OrderStatus?,
    onApply: (OrderStatus?) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedOption by remember { mutableStateOf(currentFilter) }

    val options: List<Pair<String, OrderStatus?>> = listOf(
        "All" to null,
        "Pending" to OrderStatus.PENDING,
        "Unshipped" to OrderStatus.UNSHIPPED,
        "Shipped" to OrderStatus.SHIPPED,
        "Delivered" to OrderStatus.DELIVERED,
        "Canceled" to OrderStatus.CANCELED
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "Filter by status",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = Color(0xFFEEEEEE))

            options.forEach { (label, status) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = selectedOption == status,
                            onClick = { selectedOption = status },
                            role = Role.RadioButton
                        )
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedOption == status,
                        onClick = { selectedOption = status },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = AmazonCartLinkBlue
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = label,
                        fontSize = 14.sp,
                        color = Color(0xFF333333)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Apply button
            Button(
                onClick = { onApply(selectedOption) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AmazonCheckoutYellow),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Apply",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Cancel button
            TextButton(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "Cancel",
                    fontSize = 16.sp,
                    color = AmazonCartLinkBlue
                )
            }
        }
    }
}
