package com.example.amazon_sim.ui.screen.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.amazon_sim.data.CartManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

enum class StockStatusType {
    IN_STOCK, LOW_STOCK, FREE_RETURN
}

data class CartItemUi(
    val id: String,
    val productId: String = "",
    val productName: String,
    val price: Double,
    val quantity: Int,
    val isSelected: Boolean,
    val stockStatus: String,
    val stockStatusType: StockStatusType,
    val placeholderColor: Long = 0xFFCCCCCC,
    val imageAssetPath: String = "",
    val showViewInRoom: Boolean = false
)

class CartViewModel(application: Application) : AndroidViewModel(application) {

    init {
        CartManager.init(application)
    }

    val cartItems: StateFlow<List<CartItemUi>> = CartManager.cartItems

    private val _savedForLaterName = MutableStateFlow<String?>(null)
    val savedForLaterName: StateFlow<String?> = _savedForLaterName.asStateFlow()

    val subtotal: StateFlow<Double> = cartItems.map { items ->
        items.filter { it.isSelected }.sumOf { it.price * it.quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val selectedCount: StateFlow<Int> = cartItems.map { items ->
        items.filter { it.isSelected }.sumOf { it.quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val isAllSelected: StateFlow<Boolean> = cartItems.map { items ->
        items.isNotEmpty() && items.all { it.isSelected }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val context get() = getApplication<Application>()

    fun toggleItemSelection(itemId: String) {
        CartManager.updateItems(context, cartItems.value.map {
            if (it.id == itemId) it.copy(isSelected = !it.isSelected) else it
        })
    }

    fun toggleSelectAll() {
        val allSelected = isAllSelected.value
        CartManager.updateItems(context, cartItems.value.map { it.copy(isSelected = !allSelected) })
    }

    fun increaseQuantity(itemId: String) {
        CartManager.updateItems(context, cartItems.value.map {
            if (it.id == itemId) it.copy(quantity = it.quantity + 1) else it
        })
    }

    fun decreaseQuantity(itemId: String) {
        val item = cartItems.value.find { it.id == itemId } ?: return
        if (item.quantity <= 1) {
            deleteItem(itemId)
        } else {
            CartManager.updateItems(context, cartItems.value.map {
                if (it.id == itemId) it.copy(quantity = it.quantity - 1) else it
            })
        }
    }

    fun deleteItem(itemId: String) {
        CartManager.updateItems(context, cartItems.value.filter { it.id != itemId })
    }

    fun saveForLater(itemId: String) {
        val item = cartItems.value.find { it.id == itemId } ?: return
        _savedForLaterName.value = item.productName
        CartManager.updateItems(context, cartItems.value.filter { it.id != itemId })
    }

    fun dismissSavedNotice() {
        _savedForLaterName.value = null
    }
}
