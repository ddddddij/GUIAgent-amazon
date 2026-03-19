package com.example.amazon_sim.ui.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.ui.screen.home.DealProduct
import com.example.amazon_sim.ui.theme.AmazonDealRed
import com.example.amazon_sim.ui.theme.AmazonTextBlack

@Composable
fun HomeKeepShoppingSection(
    title: String,
    products: List<DealProduct>,
    onProductClick: (String) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = AmazonTextBlack,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(products) { product ->
                Card(
                    modifier = Modifier.width(170.dp).clickable { onProductClick(product.id) },
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .background(product.placeholderColor, RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = product.name, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = AmazonTextBlack, maxLines = 2)
                        Spacer(modifier = Modifier.height(4.dp))
                        if (product.isLimitedTimeDeal) {
                            Text(text = "Limited time deal", color = AmazonDealRed, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(text = "$${String.format("%.2f", product.price)}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AmazonTextBlack)
                    }
                }
            }
        }
    }
}
