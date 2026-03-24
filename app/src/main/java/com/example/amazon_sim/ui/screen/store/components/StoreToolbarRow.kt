package com.example.amazon_sim.ui.screen.store.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.ui.theme.AmazonCartLinkBlue

@Composable
fun StoreToolbarRow(
    isFollowing: Boolean,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onFollowClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left icons
        IconButton(onClick = onMenuClick, modifier = Modifier.size(36.dp)) {
            Text("☰", fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.width(4.dp))
        IconButton(onClick = onSearchClick, modifier = Modifier.size(36.dp)) {
            Text("🔍", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Follow button
        val borderColor = if (isFollowing) AmazonCartLinkBlue else Color(0xFFCCCCCC)
        val textColor = if (isFollowing) AmazonCartLinkBlue else Color(0xFF333333)
        OutlinedButton(
            onClick = onFollowClick,
            shape = RoundedCornerShape(24.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
            modifier = Modifier.height(36.dp)
        ) {
            Text(
                text = if (isFollowing) "Following" else "Follow",
                fontSize = 14.sp,
                color = textColor
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Share icon
        IconButton(onClick = onShareClick, modifier = Modifier.size(36.dp)) {
            Text("⬆", fontSize = 18.sp)
        }
    }
}
