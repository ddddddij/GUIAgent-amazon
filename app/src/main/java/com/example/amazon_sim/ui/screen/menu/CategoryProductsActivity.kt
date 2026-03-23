package com.example.amazon_sim.ui.screen.menu

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.amazon_sim.data.repository.ProductRepositoryImpl
import com.example.amazon_sim.ui.screen.productdetail.ProductDetailActivity
import com.example.amazon_sim.ui.theme.Amazon_simTheme

class CategoryProductsActivity : ComponentActivity() {

    companion object {
        const val EXTRA_CATEGORY = "CATEGORY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val category = intent.getStringExtra(EXTRA_CATEGORY) ?: ""
        val products = ProductRepositoryImpl(this).getProducts()
            .filter { it.category.equals(category, ignoreCase = true) }

        setContent {
            Amazon_simTheme {
                CategoryProductsScreen(
                    category = category,
                    products = products,
                    onBackClick = { finish() },
                    onProductClick = { productId ->
                        startActivity(
                            Intent(this, ProductDetailActivity::class.java).apply {
                                putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, productId)
                            }
                        )
                    }
                )
            }
        }
    }
}
