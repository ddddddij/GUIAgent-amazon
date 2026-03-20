package com.example.amazon_sim.ui.screen.home

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import com.example.amazon_sim.data.repository.ProductRepositoryImpl
import com.example.amazon_sim.domain.model.HomeProductSectionSelector
import com.example.amazon_sim.domain.model.Product
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
    val store: String,
    val price: Double,
    val imageUrl: String,
    val discountPercent: Int,
    val isLimitedTimeDeal: Boolean,
    val placeholderColor: Color
)

data class CategoryItem(
    val id: String,
    val name: String,
    val imageUrl: String,
    val placeholderColor: Color
)

data class QuickTag(
    val label: String,
    val icon: String? = null
)

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val productRepository = ProductRepositoryImpl(application)

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

    private val _recommendedDeals = MutableStateFlow<List<DealProduct>>(emptyList())
    val recommendedDeals: StateFlow<List<DealProduct>> = _recommendedDeals.asStateFlow()

    private val _amazonDevices = MutableStateFlow<List<DealProduct>>(emptyList())
    val amazonDevices: StateFlow<List<DealProduct>> = _amazonDevices.asStateFlow()

    private val _trendingCategories = MutableStateFlow(
        listOf(
            CategoryItem("tc1", "Ceiling Fans", "image/CeilingFans.jpg", Color(0xFFDEB887)),
            CategoryItem("tc2", "Women's Sunglasses", "image/Sunglasses.jpg", Color(0xFFFFB6C1)),
            CategoryItem("tc3", "Solenoid Valves", "image/Solenoid.jpg", Color(0xFFDAA520)),
            CategoryItem("tc4", "Sports Fan Flags", "image/SportsFanFlags.jpg", Color(0xFF4169E1))
        )
    )
    val trendingCategories: StateFlow<List<CategoryItem>> = _trendingCategories.asStateFlow()

    private val _keepShoppingProducts = MutableStateFlow<List<DealProduct>>(emptyList())
    val keepShoppingProducts: StateFlow<List<DealProduct>> = _keepShoppingProducts.asStateFlow()

    private val _keepShoppingTitle = MutableStateFlow("Keep shopping")
    val keepShoppingTitle: StateFlow<String> = _keepShoppingTitle.asStateFlow()

    private val _freshFindsCategories = MutableStateFlow(
        listOf(
            CategoryItem("ff1", "Under \$50", "", Color(0xFFCCFF00)),
            CategoryItem("ff2", "Brands we love", "", Color(0xFFCCFF00)),
            CategoryItem("ff3", "Fashion", "", Color(0xFFE6E6FA)),
            CategoryItem("ff4", "Beauty", "", Color(0xFFFFE4E1))
        )
    )
    val freshFindsCategories: StateFlow<List<CategoryItem>> = _freshFindsCategories.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        val products = productRepository.getProducts()

        if (products.isEmpty()) {
            return
        }

        val sections = HomeProductSectionSelector.select(products)
        val recommendedSource = sections.recommendedDeals
        val electronics = sections.electronics
        val keepShoppingSource = sections.keepShopping

        _recommendedDeals.value = recommendedSource.mapIndexed { index, product ->
            product.toDealProduct(index = index, forceLimitedTimeDeal = true)
        }
        _amazonDevices.value = electronics.ifEmpty { products.take(6) }
            .mapIndexed { index, product ->
                product.toDealProduct(index = index + 2, forceLimitedTimeDeal = true)
            }
        _keepShoppingProducts.value = keepShoppingSource.mapIndexed { index, product ->
            product.toDealProduct(index = index + 1)
        }
        _keepShoppingTitle.value = "Keep shopping"
    }

    private fun Product.toDealProduct(
        index: Int,
        forceLimitedTimeDeal: Boolean = false
    ): DealProduct {
        val isLimitedTimeDeal = id !in NON_LIMITED_TIME_DEAL_IDS &&
            (forceLimitedTimeDeal || index % 2 == 0)

        return DealProduct(
            id = id,
            name = name,
            store = store,
            price = price,
            imageUrl = imageUrl,
            discountPercent = DISCOUNT_PATTERN[index % DISCOUNT_PATTERN.size],
            isLimitedTimeDeal = isLimitedTimeDeal,
            placeholderColor = PLACEHOLDER_COLORS[index % PLACEHOLDER_COLORS.size]
        )
    }

    private companion object {
        val NON_LIMITED_TIME_DEAL_IDS = setOf("prod_012", "prod_016")
        val DISCOUNT_PATTERN = listOf(15, 20, 25, 30, 35, 40)
        val PLACEHOLDER_COLORS = listOf(
            Color(0xFF8DB6CD),
            Color(0xFFB0C4DE),
            Color(0xFFA9A9A9),
            Color(0xFF778899),
            Color(0xFFC0C0C0),
            Color(0xFF696969),
            Color(0xFF87CEEB),
            Color(0xFF6495ED)
        )
    }
}
