package com.example.amazon_sim.ui.screen.lists

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amazon_sim.ui.screen.search.SearchActivity
import com.example.amazon_sim.ui.theme.Amazon_simTheme

class ShoppingListDetailActivity : ComponentActivity() {

    companion object {
        const val EXTRA_LIST_ID = "LIST_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val listId = intent.getStringExtra(EXTRA_LIST_ID) ?: ""

        setContent {
            Amazon_simTheme {
                val viewModel: ShoppingListDetailViewModel = viewModel()

                LaunchedEffect(listId) {
                    viewModel.loadList(listId)
                }

                val shoppingList by viewModel.list.collectAsStateWithLifecycle()
                val products by viewModel.products.collectAsStateWithLifecycle()

                ShoppingListDetailScreen(
                    shoppingList = shoppingList,
                    products = products,
                    onBackClick = { finish() },
                    onSearchClick = {
                        startActivity(Intent(this, SearchActivity::class.java))
                    }
                )
            }
        }
    }
}
