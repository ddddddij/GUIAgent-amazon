package com.example.amazon_sim.ui.screen.buyagain

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amazon_sim.ui.screen.order.OrderActivity
import com.example.amazon_sim.ui.screen.productdetail.ProductDetailActivity
import com.example.amazon_sim.ui.theme.Amazon_simTheme

class BuyAgainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Amazon_simTheme {
                val viewModel: BuyAgainViewModel = viewModel()
                val topSellers by viewModel.topSellers.collectAsStateWithLifecycle()

                BuyAgainScreen(
                    topSellers = topSellers,
                    onBackClick = { finish() },
                    onProductClick = { productId ->
                        startActivity(
                            Intent(this, ProductDetailActivity::class.java).apply {
                                putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, productId)
                            }
                        )
                    },
                    onYourOrdersClick = {
                        startActivity(Intent(this, OrderActivity::class.java))
                    }
                )
            }
        }
    }
}
