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

class ShoppingListDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val productRepository = ProductRepositoryImpl(application)
    private val allProducts: List<Product> = productRepository.getProducts()

    init {
        ListsRepository.init(application)
    }

    private val _list = MutableStateFlow<ShoppingList?>(null)
    val list: StateFlow<ShoppingList?> = _list.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    fun loadList(listId: String) {
        val shoppingList = ListsRepository.getListById(listId)
        _list.value = shoppingList
        _products.value = shoppingList?.productIds?.mapNotNull { pid ->
            allProducts.find { it.id == pid }
        } ?: emptyList()
    }
}
