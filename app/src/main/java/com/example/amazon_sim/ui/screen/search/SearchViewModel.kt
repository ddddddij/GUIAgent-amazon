package com.example.amazon_sim.ui.screen.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.amazon_sim.data.repository.ProductRepositoryImpl
import com.example.amazon_sim.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val productRepository = ProductRepositoryImpl(application)
    private val allProducts: List<Product> = productRepository.getProducts()

    private val _keyword = MutableStateFlow("")
    val keyword: StateFlow<String> = _keyword.asStateFlow()

    private val _suggestions = MutableStateFlow<List<Product>>(allProducts)
    val suggestions: StateFlow<List<Product>> = _suggestions.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Product>>(emptyList())
    val searchResults: StateFlow<List<Product>> = _searchResults.asStateFlow()

    fun updateKeyword(text: String) {
        _keyword.value = text
        _suggestions.value = if (text.isBlank()) {
            allProducts
        } else {
            filterProducts(text)
        }
    }

    fun search(keyword: String) {
        _keyword.value = keyword
        _searchResults.value = filterProducts(keyword)
    }

    private fun filterProducts(keyword: String): List<Product> {
        val lower = keyword.lowercase()
        return allProducts.filter { product ->
            product.name.lowercase().contains(lower) ||
                product.brandName.lowercase().contains(lower) ||
                product.productType.lowercase().contains(lower) ||
                product.category.lowercase().contains(lower)
        }
    }
}
