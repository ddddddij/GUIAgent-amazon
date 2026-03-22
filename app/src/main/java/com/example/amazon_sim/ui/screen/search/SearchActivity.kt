package com.example.amazon_sim.ui.screen.search

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amazon_sim.ui.theme.Amazon_simTheme

class SearchActivity : ComponentActivity() {

    companion object {
        const val EXTRA_INITIAL_KEYWORD = "INITIAL_KEYWORD"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val initialKeyword = intent.getStringExtra(EXTRA_INITIAL_KEYWORD) ?: ""

        setContent {
            Amazon_simTheme {
                val viewModel: SearchViewModel = viewModel()

                LaunchedEffect(initialKeyword) {
                    if (initialKeyword.isNotBlank()) {
                        viewModel.updateKeyword(initialKeyword)
                    }
                }

                val keyword by viewModel.keyword.collectAsStateWithLifecycle()
                val suggestions by viewModel.suggestions.collectAsStateWithLifecycle()

                SearchScreen(
                    keyword = keyword,
                    suggestions = suggestions,
                    onKeywordChange = { viewModel.updateKeyword(it) },
                    onSearch = { query ->
                        if (query.isNotBlank()) {
                            startActivity(
                                Intent(this, SearchResultActivity::class.java).apply {
                                    putExtra(SearchResultActivity.EXTRA_KEYWORD, query)
                                }
                            )
                        }
                    },
                    onSuggestionClick = { name ->
                        startActivity(
                            Intent(this, SearchResultActivity::class.java).apply {
                                putExtra(SearchResultActivity.EXTRA_KEYWORD, name)
                            }
                        )
                    },
                    onBackClick = { finish() }
                )
            }
        }
    }
}
