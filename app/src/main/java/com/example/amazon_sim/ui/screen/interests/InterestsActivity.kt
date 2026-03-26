package com.example.amazon_sim.ui.screen.interests

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amazon_sim.MainActivity
import com.example.amazon_sim.data.repository.ListsRepository
import com.example.amazon_sim.ui.screen.productdetail.ProductDetailActivity
import com.example.amazon_sim.ui.screen.search.SearchActivity
import com.example.amazon_sim.ui.theme.Amazon_simTheme

class InterestsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ListsRepository.init(this)

        setContent {
            Amazon_simTheme {
                val viewModel: InterestsViewModel = viewModel()
                val categories by viewModel.categories.collectAsStateWithLifecycle()
                val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
                val filteredProducts by viewModel.filteredProducts.collectAsStateWithLifecycle()
                val shoppingLists by ListsRepository.lists.collectAsStateWithLifecycle()

                val alexaListProductIds = shoppingLists
                    .find { it.listId == "list_alexa" }
                    ?.productIds?.toSet() ?: emptySet()

                InterestsScreen(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    filteredProducts = filteredProducts,
                    alexaListProductIds = alexaListProductIds,
                    onBackClick = { finish() },
                    onSearchClick = {
                        startActivity(Intent(this, SearchActivity::class.java))
                    },
                    onPlusClick = {
                        val intent = Intent(this, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                            putExtra(MainActivity.EXTRA_NAVIGATE_HOME, true)
                        }
                        startActivity(intent)
                    },
                    onCategoryClick = { category ->
                        viewModel.selectCategory(category)
                    },
                    onProductClick = { productId ->
                        startActivity(
                            Intent(this, ProductDetailActivity::class.java).apply {
                                putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, productId)
                            }
                        )
                    },
                    onFavoriteClick = { productId ->
                        ListsRepository.addProductToList(this, "list_alexa", productId)
                        Toast.makeText(this, "Added to Alexa List", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}
