package com.example.amazon_sim.ui.screen.customerservice.components

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingBubble(
    showLabel: Boolean,
    modifier: Modifier = Modifier
) {
    val transition = rememberInfiniteTransition(label = "loading")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 64.dp)
    ) {
        if (showLabel) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                AmazonAvatar()
                Spacer(modifier = Modifier.width(6.dp))
                androidx.compose.material3.Text(
                    text = "Amazon Assistant",
                    fontSize = androidx.compose.ui.unit.TextUnit(12f, androidx.compose.ui.unit.TextUnitType.Sp),
                    color = Color.Gray
                )
            }
        }

        Box(
            modifier = Modifier.padding(start = if (showLabel) 0.dp else 34.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 16.dp,
                    bottomEnd = 16.dp,
                    bottomStart = 16.dp
                ),
                color = Color(0xFFF5F5F5),
                modifier = Modifier.widthIn(max = 80.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    for (i in 0..2) {
                        BouncingDot(transition = transition, delayMillis = i * 150)
                        if (i < 2) Spacer(modifier = Modifier.width(6.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun BouncingDot(
    transition: InfiniteTransition,
    delayMillis: Int
) {
    val offset by transition.animateFloat(
        initialValue = 0f,
        targetValue = -6f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 400, delayMillis = delayMillis),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot_$delayMillis"
    )

    Surface(
        shape = CircleShape,
        color = Color(0xFF999999),
        modifier = Modifier
            .size(8.dp)
            .offset(y = offset.dp)
    ) {}
}
