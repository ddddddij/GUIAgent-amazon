package com.example.amazon_sim.ui.screen.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.ui.screen.profile.ProfileQuickAction
import com.example.amazon_sim.ui.theme.AmazonBackground
import com.example.amazon_sim.ui.theme.AmazonSearchBarBorder
import com.example.amazon_sim.ui.theme.AmazonTextDark

@Composable
fun ProfileQuickActionRow(
    actions: List<ProfileQuickAction>,
    onActionClick: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        actions.forEach { action ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .border(1.dp, AmazonSearchBarBorder, RoundedCornerShape(24.dp))
                    .background(AmazonBackground, RoundedCornerShape(24.dp))
                    .clickable { onActionClick(action.id) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = action.label,
                    color = AmazonTextDark,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    }
}
