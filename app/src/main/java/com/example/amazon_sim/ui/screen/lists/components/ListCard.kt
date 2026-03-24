package com.example.amazon_sim.ui.screen.lists.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.ui.screen.home.components.HomeProductAssetImage

@Composable
fun ListCard(
    listName: String,
    coverImagePath: String?,
    isAlexaList: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .width(200.dp)
            .height(80.dp)
            .border(1.dp, Color(0xFFDDDDDD), RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Cover image
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
        ) {
            if (isAlexaList) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .background(Color(0xFF00897B)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Alexa\nShopping\nList",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 12.sp
                    )
                }
            } else if (coverImagePath != null) {
                HomeProductAssetImage(
                    assetPath = coverImagePath,
                    contentDescription = listName,
                    fallbackColor = Color(0xFFCCCCCC)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .background(Color(0xFFEEEEEE))
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // List name
        Text(
            text = listName,
            fontSize = 15.sp,
            color = Color.Black,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
    }
}
