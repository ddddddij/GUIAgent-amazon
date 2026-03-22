package com.example.amazon_sim.ui.screen.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amazon_sim.ui.screen.cart.components.CartItemCard
import com.example.amazon_sim.ui.screen.cart.components.CartSearchBar
import com.example.amazon_sim.ui.screen.cart.components.CheckoutHeader
import com.example.amazon_sim.ui.theme.AmazonCartLinkBlue
import com.example.amazon_sim.ui.theme.AmazonDividerGray
import com.example.amazon_sim.ui.theme.AmazonProfileTopBackground

@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(),
    onSearchClick: () -> Unit = {}
) {
    val cartItems by viewModel.cartItems.collectAsState()
    val subtotal by viewModel.subtotal.collectAsState()
    val selectedCount by viewModel.selectedCount.collectAsState()
    val savedForLaterName by viewModel.savedForLaterName.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Search bar with orange background
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AmazonProfileTopBackground)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            CartSearchBar(onSearchClick = onSearchClick)
        }

        // Sticky header: subtotal + checkout button
        CheckoutHeader(
            subtotal = subtotal,
            selectedCount = selectedCount
        )

        HorizontalDivider(color = AmazonDividerGray, thickness = 1.dp)

        // Scrollable content
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            // Select all items
            item {
                Text(
                    text = "Select all items",
                    color = AmazonCartLinkBlue,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clickable { viewModel.toggleSelectAll() }
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }

            // Saved for later notice
            if (savedForLaterName != null) {
                item {
                    Text(
                        text = "${savedForLaterName?.take(40)}... has been moved to Saved for Later.",
                        color = AmazonCartLinkBlue,
                        fontSize = 14.sp,
                        textDecoration = TextDecoration.Underline,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable { viewModel.dismissSavedNotice() }
                    )
                }
            }

            // Cart items
            items(cartItems, key = { it.id }) { item ->
                CartItemCard(
                    item = item,
                    onToggleSelect = { viewModel.toggleItemSelection(item.id) },
                    onIncrease = { viewModel.increaseQuantity(item.id) },
                    onDecrease = { viewModel.decreaseQuantity(item.id) },
                    onDelete = { viewModel.deleteItem(item.id) },
                    onSaveForLater = { viewModel.saveForLater(item.id) }
                )
                HorizontalDivider(color = AmazonDividerGray, thickness = 1.dp)
            }

            // Bottom spacing
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
