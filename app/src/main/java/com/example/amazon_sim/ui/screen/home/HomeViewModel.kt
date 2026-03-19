package com.example.amazon_sim.ui.screen.home

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class BannerItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val buttonText: String,
    val backgroundColor: Color
)

data class DealProduct(
    val id: String,
    val name: String,
    val price: Double,
    val discountPercent: Int,
    val isLimitedTimeDeal: Boolean,
    val placeholderColor: Color
)

data class CategoryItem(
    val id: String,
    val name: String,
    val placeholderColor: Color
)

data class QuickTag(
    val label: String,
    val icon: String? = null
)

class HomeViewModel : ViewModel() {

    private val _deliveryAddress = MutableStateFlow("Los Angeles 90017")
    val deliveryAddress: StateFlow<String> = _deliveryAddress.asStateFlow()

    private val _quickTags = MutableStateFlow(
        listOf(
            QuickTag("Join Prime"),
            QuickTag("Same-Day"),
            QuickTag("Groceries"),
            QuickTag("Deals"),
            QuickTag("Fashion"),
            QuickTag("Electronics")
        )
    )
    val quickTags: StateFlow<List<QuickTag>> = _quickTags.asStateFlow()

    private val _banners = MutableStateFlow(
        listOf(
            BannerItem("b1", "Prime member exclusive", "Save up to 30% on deals", "Join Prime", Color(0xFF0066FF)),
            BannerItem("b2", "Big Spring Sale", "Shop early deals now", "Shop now", Color(0xFFFFB6D9)),
            BannerItem("b3", "Fresh Groceries", "Fast delivery to your door", "Shop Fresh", Color(0xFF00A86B))
        )
    )
    val banners: StateFlow<List<BannerItem>> = _banners.asStateFlow()

    private val _recommendedDeals = MutableStateFlow(
        listOf(
            DealProduct("d1", "Tablet Case with Stand", 29.99, 10, true, Color(0xFF8DB6CD)),
            DealProduct("d2", "Stylus Pen for Tablet", 14.99, 47, true, Color(0xFFB0C4DE)),
            DealProduct("d3", "Laptop 15.6 inch", 499.99, 40, true, Color(0xFFA9A9A9)),
            DealProduct("d4", "Laptop Sleeve Case", 19.99, 35, true, Color(0xFF778899)),
            DealProduct("d5", "iPad 10th Gen", 349.99, 15, true, Color(0xFFC0C0C0)),
            DealProduct("d6", "Gaming Laptop", 899.99, 34, true, Color(0xFF696969))
        )
    )
    val recommendedDeals: StateFlow<List<DealProduct>> = _recommendedDeals.asStateFlow()

    private val _amazonDevices = MutableStateFlow(
        listOf(
            DealProduct("ad1", "Football Helmet", 89.99, 20, true, Color(0xFFCD853F)),
            DealProduct("ad2", "Security Camera", 49.99, 33, true, Color(0xFF708090)),
            DealProduct("ad3", "Hisense 55\" Smart TV", 329.99, 39, true, Color(0xFF2F4F4F)),
            DealProduct("ad4", "Skull Speaker", 39.99, 50, true, Color(0xFF4A4A4A)),
            DealProduct("ad5", "Ring Video Doorbell", 79.99, 25, true, Color(0xFF87CEEB)),
            DealProduct("ad6", "Echo Dot Speaker", 29.99, 40, true, Color(0xFF483D8B))
        )
    )
    val amazonDevices: StateFlow<List<DealProduct>> = _amazonDevices.asStateFlow()

    private val _trendingCategories = MutableStateFlow(
        listOf(
            CategoryItem("tc1", "Ceiling Fans", Color(0xFFDEB887)),
            CategoryItem("tc2", "Women's Sunglasses", Color(0xFFFFB6C1)),
            CategoryItem("tc3", "Solenoid Valves", Color(0xFFDAA520)),
            CategoryItem("tc4", "Sports Fan Flags", Color(0xFF4169E1))
        )
    )
    val trendingCategories: StateFlow<List<CategoryItem>> = _trendingCategories.asStateFlow()

    private val _keepShoppingProducts = MutableStateFlow(
        listOf(
            DealProduct("ks1", "Tablet Protective Case", 24.99, 50, true, Color(0xFF6495ED)),
            DealProduct("ks2", "iPad Keyboard Case", 39.99, 30, false, Color(0xFF7B68EE)),
            DealProduct("ks3", "Tablet Stand Holder", 15.99, 20, false, Color(0xFF9370DB)),
            DealProduct("ks4", "Screen Protector Pack", 9.99, 15, false, Color(0xFFBA55D3))
        )
    )
    val keepShoppingProducts: StateFlow<List<DealProduct>> = _keepShoppingProducts.asStateFlow()

    private val _freshFindsCategories = MutableStateFlow(
        listOf(
            CategoryItem("ff1", "Under \$50", Color(0xFFCCFF00)),
            CategoryItem("ff2", "Brands we love", Color(0xFFCCFF00)),
            CategoryItem("ff3", "Fashion", Color(0xFFE6E6FA)),
            CategoryItem("ff4", "Beauty", Color(0xFFFFE4E1))
        )
    )
    val freshFindsCategories: StateFlow<List<CategoryItem>> = _freshFindsCategories.asStateFlow()
}
