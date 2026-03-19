package com.example.amazon_sim.ui.screen.menu.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ShortcutsSection(
    shortcuts: List<String>,
    onShortcutClick: (String) -> Unit = {}
) {
    Column(modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)) {
        Text(
            text = "Your shortcuts",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(shortcuts) { label ->
                Text(
                    text = label,
                    fontSize = 14.sp,
                    color = Color(0xFF333333),
                    modifier = Modifier
                        .border(1.dp, Color(0xFFDDDDDD), RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onShortcutClick(label) }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}
