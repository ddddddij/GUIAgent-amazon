package com.example.amazon_sim.ui.screen.store

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amazon_sim.data.CartManager
import com.example.amazon_sim.ui.screen.productdetail.ProductDetailActivity
import com.example.amazon_sim.ui.theme.Amazon_simTheme

class StoreActivity : ComponentActivity() {

    companion object {
        const val EXTRA_BRAND_ID = "BRAND_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        CartManager.init(this)

        val brandId = intent.getStringExtra(EXTRA_BRAND_ID) ?: ""

        setContent {
            Amazon_simTheme {
                val viewModel: StoreViewModel = viewModel()

                LaunchedEffect(brandId) {
                    viewModel.loadStore(brandId)
                }

                val brand by viewModel.brand.collectAsStateWithLifecycle()
                val storeCards by viewModel.storeCards.collectAsStateWithLifecycle()
                val isFollowing by viewModel.isFollowing.collectAsStateWithLifecycle()

                StoreScreen(
                    brand = brand,
                    storeCards = storeCards,
                    isFollowing = isFollowing,
                    onBackClick = { finish() },
                    onFollowClick = { viewModel.toggleFollow() },
                    onProductClick = { productId ->
                        startActivity(
                            Intent(this, ProductDetailActivity::class.java).apply {
                                putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, productId)
                            }
                        )
                    },
                    onAddToCartClick = { card ->
                        CartManager.addToCart(
                            context = this,
                            productName = card.cardTitle,
                            price = card.currentPrice,
                            quantity = 1,
                            placeholderColor = 0xFFCCCCCC,
                            imageAssetPath = card.imageAssetPath,
                            productId = card.productId
                        )
                    }
                )
            }
        }
    }
}
