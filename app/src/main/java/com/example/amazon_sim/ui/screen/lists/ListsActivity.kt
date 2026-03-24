package com.example.amazon_sim.ui.screen.lists

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amazon_sim.data.repository.ProductRepositoryImpl
import com.example.amazon_sim.ui.screen.search.SearchActivity
import com.example.amazon_sim.ui.theme.Amazon_simTheme

class ListsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val allProducts = ProductRepositoryImpl(this).getProducts()

        setContent {
            Amazon_simTheme {
                val viewModel: ListsViewModel = viewModel()
                val lists by viewModel.lists.collectAsStateWithLifecycle()
                val allSavedProducts by viewModel.allSavedProducts.collectAsStateWithLifecycle()
                val suggestion by viewModel.newListNameSuggestion.collectAsStateWithLifecycle()

                ListsScreen(
                    lists = lists,
                    allSavedProducts = allSavedProducts,
                    newListNameSuggestion = suggestion,
                    allProducts = allProducts,
                    onBackClick = { finish() },
                    onSearchClick = {
                        startActivity(Intent(this, SearchActivity::class.java))
                    },
                    onListCardClick = { listId ->
                        startActivity(
                            Intent(this, ShoppingListDetailActivity::class.java).apply {
                                putExtra(ShoppingListDetailActivity.EXTRA_LIST_ID, listId)
                            }
                        )
                    },
                    onCreateList = { name ->
                        viewModel.createList(name)
                    }
                )
            }
        }
    }
}
