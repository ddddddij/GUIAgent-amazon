package com.example.amazon_sim.ui.screen.order

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amazon_sim.ui.theme.Amazon_simTheme

class OrderDetailActivity : ComponentActivity() {

    companion object {
        const val EXTRA_ORDER_ID = "ORDER_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val orderId = intent.getStringExtra(EXTRA_ORDER_ID) ?: ""

        setContent {
            Amazon_simTheme {
                val viewModel: OrderDetailViewModel = viewModel()

                LaunchedEffect(orderId) {
                    viewModel.loadOrder(orderId)
                }

                val order by viewModel.currentOrder.collectAsStateWithLifecycle()

                val currentOrder = order
                if (currentOrder != null) {
                    OrderDetailScreen(
                        order = currentOrder,
                        onBackClick = { finish() },
                        onCancelOrder = { viewModel.cancelOrder() },
                        onConfirmReceipt = { viewModel.confirmReceipt() }
                    )
                }
            }
        }
    }
}
