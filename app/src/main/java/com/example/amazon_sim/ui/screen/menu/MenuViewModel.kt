package com.example.amazon_sim.ui.screen.menu

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MenuUiState(
    val shortcuts: List<String>,
    val isDepartmentExpanded: Boolean,
    val categories: List<String>,
    val bottomActions: List<String>
)

class MenuViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        MenuUiState(
            shortcuts = listOf("Lists", "Orders", "Buy Again", "Account"),
            isDepartmentExpanded = true,
            categories = listOf(
                "Electronics",
                "Lifestyle",
                "Sports",
                "Food",
                "Arts & Crafts",
                "Automotive Parts & Accessories",
                "Baby",
                "Beauty & Personal Care",
                "Cars",
                "Computers",
                "Books",
                "Digital Music",
                "Women's Fashion",
                "Men's Fashion",
                "Girls' Fashion",
                "Luggage",
                "Movies & Television",
                "Music, CDs & Vinyl",
                "Pet Supplies",
                "Prime Video",
                "Software",
                "Sports & Outdoors",
                "Tools & Home Improvement",
                "Toys & Games",
                "Video Games",
                "Deals"
            ),
            bottomActions = listOf("Switch Accounts", "Sign Out", "Customer Service")
        )
    )
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

    fun toggleDepartmentExpanded() {
        _uiState.value = _uiState.value.copy(
            isDepartmentExpanded = !_uiState.value.isDepartmentExpanded
        )
    }
}
