package com.example.amazon_sim.ui.screen.lists.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetailTitleRow(
    listName: String,
    onAddItemClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = listName,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )

        OutlinedButton(
            onClick = onAddItemClick,
            modifier = Modifier.height(36.dp),
            shape = RoundedCornerShape(24.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCCCCCC))
        ) {
            Text(
                text = "Add item",
                fontSize = 14.sp,
                color = Color(0xFF333333)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = onMoreClick,
            modifier = Modifier
                .size(36.dp)
                .border(1.dp, Color(0xFFCCCCCC), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Filled.MoreHoriz,
                contentDescription = "More",
                tint = Color(0xFF555555),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
