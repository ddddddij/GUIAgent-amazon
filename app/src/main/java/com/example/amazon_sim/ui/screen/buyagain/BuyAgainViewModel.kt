package com.example.amazon_sim.ui.screen.buyagain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.amazon_sim.data.repository.ProductRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class TopSellerCardData(
    val productId: String,
    val name: String,
    val imageAssetPath: String,
    val specSubtitle: String,
    val rating: Double,
    val reviewCount: Int,
    val discountPercent: Int,
    val price: Double,
    val typicalPrice: Double
)

class BuyAgainViewModel(application: Application) : AndroidViewModel(application) {

    private val _topSellers = MutableStateFlow<List<TopSellerCardData>>(emptyList())
    val topSellers: StateFlow<List<TopSellerCardData>> = _topSellers

    init {
        val products = ProductRepositoryImpl(application).getTopSellers(10)
        _topSellers.value = products.map { p ->
            TopSellerCardData(
                productId = p.id,
                name = p.name,
                imageAssetPath = p.imageUrl,
                specSubtitle = p.specSubtitle,
                rating = p.rating,
                reviewCount = p.reviewCount,
                discountPercent = if (p.typicalPrice > p.price)
                    ((p.typicalPrice - p.price) / p.typicalPrice * 100).toInt() else 0,
                price = p.price,
                typicalPrice = p.typicalPrice
            )
        }
    }
}
