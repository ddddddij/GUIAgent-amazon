package com.example.amazon_sim.ui.screen.interests

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.amazon_sim.data.repository.ProductRepositoryImpl
import com.example.amazon_sim.domain.model.InterestCategory
import com.example.amazon_sim.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InterestsViewModel(application: Application) : AndroidViewModel(application) {

    private val productRepository = ProductRepositoryImpl(application)
    private val allProducts: List<Product> = productRepository.getProducts()

    private val _categories = MutableStateFlow(PRESET_CATEGORIES)
    val categories: StateFlow<List<InterestCategory>> = _categories.asStateFlow()

    private val _selectedCategory = MutableStateFlow<InterestCategory?>(PRESET_CATEGORIES.firstOrNull())
    val selectedCategory: StateFlow<InterestCategory?> = _selectedCategory.asStateFlow()

    private val _filteredProducts = MutableStateFlow<List<Product>>(emptyList())
    val filteredProducts: StateFlow<List<Product>> = _filteredProducts.asStateFlow()

    init {
        updateFilteredProducts()
    }

    fun selectCategory(category: InterestCategory) {
        _selectedCategory.value = category
        updateFilteredProducts()
    }

    private fun updateFilteredProducts() {
        val category = _selectedCategory.value
        _filteredProducts.value = if (category == null) {
            emptyList()
        } else {
            allProducts
                .filter { product ->
                    category.productTypes.any { type ->
                        product.productType.contains(type, ignoreCase = true)
                    }
                }
                .sortedBy { it.repurchaseRate }
        }
    }

    companion object {
        private val PRESET_CATEGORIES = listOf(
            InterestCategory(
                id = "electronics",
                displayName = "Electronics",
                imageAssetPath = "image/Sony.jpg",
                productTypes = listOf("smartphone", "laptop", "speaker", "electronics", "headphones", "gaming console", "smartwatch")
            ),
            InterestCategory(
                id = "lifestyle",
                displayName = "Lifestyle",
                imageAssetPath = null,
                productTypes = listOf("vacuum", "coffee maker", "mixer", "blender")
            ),
            InterestCategory(
                id = "sports",
                displayName = "Sports",
                imageAssetPath = null,
                productTypes = listOf("running shoes", "soccer ball", "basketball")
            ),
            InterestCategory(
                id = "food",
                displayName = "Food",
                imageAssetPath = null,
                productTypes = listOf("coffee", "beverage", "cookies")
            )
        )
    }
}
