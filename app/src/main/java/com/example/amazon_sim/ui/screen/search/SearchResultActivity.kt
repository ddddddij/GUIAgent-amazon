package com.example.amazon_sim.ui.screen.search

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amazon_sim.ui.screen.productdetail.ProductDetailActivity
import com.example.amazon_sim.ui.theme.Amazon_simTheme

class SearchResultActivity : ComponentActivity() {

    companion object {
        const val EXTRA_KEYWORD = "KEYWORD"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val keyword = intent.getStringExtra(EXTRA_KEYWORD) ?: ""

        setContent {
            Amazon_simTheme {
                val viewModel: SearchViewModel = viewModel()

                LaunchedEffect(keyword) {
                    viewModel.search(keyword)
                }

                val currentKeyword by viewModel.keyword.collectAsStateWithLifecycle()
                val results by viewModel.searchResults.collectAsStateWithLifecycle()

                SearchResultScreen(
                    keyword = currentKeyword,
                    results = results,
                    onBackClick = { finish() },
                    onSearchBarClick = {
                        startActivity(
                            Intent(this, SearchActivity::class.java).apply {
                                putExtra(SearchActivity.EXTRA_INITIAL_KEYWORD, currentKeyword)
                            }
                        )
                    },
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
