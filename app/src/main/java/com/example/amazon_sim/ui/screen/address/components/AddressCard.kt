package com.example.amazon_sim.ui.screen.address.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
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
import com.example.amazon_sim.domain.model.Address
import com.example.amazon_sim.ui.theme.AmazonLinkBlue
import com.example.amazon_sim.ui.theme.AmazonSearchBarBorder

@Composable
fun AddressCard(
    address: Address,
    onEditClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onCardClick: (() -> Unit)? = null
) {
    val localityLine = listOf(
        address.city.takeIf { it.isNotBlank() },
        address.state.takeIf { it.isNotBlank() },
        address.zipCode.takeIf { it.isNotBlank() }
    ).joinToString(separator = ", ") { it.orEmpty() }
        .replace(", , ", ", ")
        .trim(',', ' ')

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .then(
                if (onCardClick != null) Modifier.clickable(onClick = onCardClick)
                else Modifier
            ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color(0xFFDDDDDD)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (address.isDefault) {
                Text(
                    text = buildAnnotatedString {
                        append("Default: ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("amazon")
                        }
                    },
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                text = address.fullName,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = address.streetAddress + if (address.aptSuite.isNotEmpty()) ", ${address.aptSuite}" else "",
                fontSize = 14.sp,
                color = Color.Black
            )
            if (localityLine.isNotEmpty()) {
                Text(
                    text = localityLine,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
            Text(
                text = address.country,
                fontSize = 14.sp,
                color = Color.Black
            )
            if (address.phoneNumber.isNotEmpty()) {
                Text(
                    text = "Phone number: ${address.phoneNumber}",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Add delivery instructions",
                fontSize = 14.sp,
                color = AmazonLinkBlue
            )

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onEditClick,
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, AmazonSearchBarBorder)
                ) {
                    Text("Edit", color = Color.Black, fontSize = 14.sp)
                }
                OutlinedButton(
                    onClick = onRemoveClick,
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, AmazonSearchBarBorder)
                ) {
                    Text("Remove", color = Color.Black, fontSize = 14.sp)
                }
            }
        }
    }
}
