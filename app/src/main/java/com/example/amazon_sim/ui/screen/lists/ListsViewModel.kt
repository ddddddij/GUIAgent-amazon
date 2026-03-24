package com.example.amazon_sim.ui.screen.lists

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.amazon_sim.data.repository.ListsRepository
import com.example.amazon_sim.data.repository.ProductRepositoryImpl
import com.example.amazon_sim.domain.model.Product
import com.example.amazon_sim.domain.model.ShoppingList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListsViewModel(application: Application) : AndroidViewModel(application) {

    private val productRepository = ProductRepositoryImpl(application)
    private val allProducts: List<Product> = productRepository.getProducts()

    init {
        ListsRepository.init(application)
    }

    val lists: StateFlow<List<ShoppingList>> = ListsRepository.lists

    private val _allSavedProducts = MutableStateFlow<List<Pair<Product, String>>>(emptyList())
    val allSavedProducts: StateFlow<List<Pair<Product, String>>> = _allSavedProducts.asStateFlow()

    private val _newListNameSuggestion = MutableStateFlow(ListsRepository.suggestNewListName())
    val newListNameSuggestion: StateFlow<String> = _newListNameSuggestion.asStateFlow()

    init {
        refreshSavedProducts()
    }

    fun createList(name: String) {
        ListsRepository.createList(getApplication(), name)
        _newListNameSuggestion.value = ListsRepository.suggestNewListName()
        refreshSavedProducts()
    }

    private fun refreshSavedProducts() {
        val currentLists = ListsRepository.lists.value
        val seen = mutableSetOf<String>()
        val result = mutableListOf<Pair<Product, String>>()
        for (list in currentLists) {
            for (pid in list.productIds) {
                if (pid in seen) continue
                seen.add(pid)
                val product = allProducts.find { it.id == pid }
                if (product != null) {
                    result.add(product to list.listName)
                }
            }
        }
        _allSavedProducts.value = result
    }
}
