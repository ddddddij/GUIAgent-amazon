package com.example.amazon_sim.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val productName: String,
    val price: Double,
    val quantity: Int,
    val isSelected: Boolean,
    val stockStatus: String,
    val stockStatusType: StockStatusType,
    val placeholderColor: Long = 0xFFCCCCCC,
    val showViewInRoom: Boolean = false
)

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow(
        listOf(
            CartItemUi(
                id = "1",
                productName = "adidas mens Aeroready Designed 2 Move Feelready Sport Tee T Shirt, White/Black",
                price = 13.95,
                quantity = 1,
                isSelected = false,
                stockStatus = "Only 3 left. Order now.",
                stockStatusType = StockStatusType.LOW_STOCK,
                placeholderColor = 0xFFE0E0E0
            ),
            CartItemUi(
                id = "2",
                productName = "Marshall Acton III Bluetooth Home Speaker - Loud Stereo Sound with Bass",
                price = 241.99,
                quantity = 1,
                isSelected = false,
                stockStatus = "In Stock",
                stockStatusType = StockStatusType.IN_STOCK,
                placeholderColor = 0xFF2E3A4F,
                showViewInRoom = true
            ),
            CartItemUi(
                id = "3",
                productName = "Apple iPhone 17 Pro Max, US Version, 256GB, eSIM, Cosmic Orange- Unlocked",
                price = 1163.99,
                quantity = 1,
                isSelected = false,
                stockStatus = "Only 7 left. Order now.",
                stockStatusType = StockStatusType.LOW_STOCK,
                placeholderColor = 0xFFFF8A65
            )
        )
    )
    val cartItems: StateFlow<List<CartItemUi>> = _cartItems.asStateFlow()

    private val _savedForLaterName = MutableStateFlow<String?>(null)
    val savedForLaterName: StateFlow<String?> = _savedForLaterName.asStateFlow()

    val subtotal: StateFlow<Double> = _cartItems.map { items ->
        items.filter { it.isSelected }.sumOf { it.price * it.quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val selectedCount: StateFlow<Int> = _cartItems.map { items ->
        items.filter { it.isSelected }.sumOf { it.quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val isAllSelected: StateFlow<Boolean> = _cartItems.map { items ->
        items.isNotEmpty() && items.all { it.isSelected }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun toggleItemSelection(itemId: String) {
        _cartItems.value = _cartItems.value.map {
            if (it.id == itemId) it.copy(isSelected = !it.isSelected) else it
        }
    }

    fun toggleSelectAll() {
        val allSelected = isAllSelected.value
        _cartItems.value = _cartItems.value.map { it.copy(isSelected = !allSelected) }
    }

    fun increaseQuantity(itemId: String) {
        _cartItems.value = _cartItems.value.map {
            if (it.id == itemId) it.copy(quantity = it.quantity + 1) else it
        }
    }

    fun decreaseQuantity(itemId: String) {
        val item = _cartItems.value.find { it.id == itemId } ?: return
        if (item.quantity <= 1) {
            deleteItem(itemId)
        } else {
            _cartItems.value = _cartItems.value.map {
                if (it.id == itemId) it.copy(quantity = it.quantity - 1) else it
            }
        }
    }

    fun deleteItem(itemId: String) {
        _cartItems.value = _cartItems.value.filter { it.id != itemId }
    }

    fun saveForLater(itemId: String) {
        val item = _cartItems.value.find { it.id == itemId } ?: return
        _savedForLaterName.value = item.productName
        _cartItems.value = _cartItems.value.filter { it.id != itemId }
    }

    fun dismissSavedNotice() {
        _savedForLaterName.value = null
    }
}
