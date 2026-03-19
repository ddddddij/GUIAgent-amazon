package com.example.amazon_sim.ui.screen.profile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.ui.theme.AmazonBackground
import com.example.amazon_sim.ui.theme.AmazonSearchBarBorder
import com.example.amazon_sim.ui.theme.AmazonTextDark

object ProfileActionRows {

    @Composable
    fun SingleActionButton(
        text: String,
        onClick: () -> Unit = {}
    ) {
        OutlinedButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = AmazonBackground,
                contentColor = AmazonTextDark
            ),
            border = BorderStroke(1.dp, AmazonSearchBarBorder)
        ) {
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = AmazonTextDark
            )
        }
    }

    @Composable
    fun TwoActionButtons(
        primaryText: String,
        secondaryText: String,
        onPrimaryClick: () -> Unit = {},
        onSecondaryClick: () -> Unit = {}
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onPrimaryClick,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = AmazonBackground,
                    contentColor = AmazonTextDark
                ),
                border = BorderStroke(1.dp, AmazonSearchBarBorder)
            ) {
                Text(
                    text = primaryText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = AmazonTextDark
                )
            }

            OutlinedButton(
                onClick = onSecondaryClick,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = AmazonBackground,
                    contentColor = AmazonTextDark
                ),
                border = BorderStroke(1.dp, AmazonSearchBarBorder)
            ) {
                Text(
                    text = secondaryText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = AmazonTextDark
                )
            }
        }
    }
}
