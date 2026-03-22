package com.example.amazon_sim.ui.screen.order

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amazon_sim.ui.screen.search.SearchActivity
import com.example.amazon_sim.ui.theme.Amazon_simTheme

class OrderActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Amazon_simTheme {
                val viewModel: OrderViewModel = viewModel()

                val filteredOrders by viewModel.filteredOrders.collectAsStateWithLifecycle()
                val currentFilter by viewModel.currentFilter.collectAsStateWithLifecycle()

                OrderScreen(
                    orders = filteredOrders,
                    currentFilter = currentFilter,
                    onFilterChange = { viewModel.setFilter(it) },
                    onBackClick = { finish() },
                    onSearchClick = {
                        startActivity(Intent(this, SearchActivity::class.java))
                    },
                    onOrderClick = { order ->
                        startActivity(
                            Intent(this, OrderDetailActivity::class.java).apply {
                                putExtra(OrderDetailActivity.EXTRA_ORDER_ID, order.orderId)
                            }
                        )
                    }
                )
            }
        }
    }
}
