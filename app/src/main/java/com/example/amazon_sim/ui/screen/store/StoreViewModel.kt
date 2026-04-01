package com.example.amazon_sim.ui.screen.store

import android.content.Context
import com.example.amazon_sim.data.repository.BrandRepository
import com.example.amazon_sim.data.repository.ProductDetailRepositoryImpl
import com.example.amazon_sim.domain.model.Brand
import com.example.amazon_sim.domain.model.ProductDetailData
import com.example.amazon_sim.domain.model.SpecGroup
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class StoreProductCard(
    val productId: String,
    val cardTitle: String,
    val imageAssetPath: String,
    val rating: Float,
    val reviewCount: Int,
    val currentPrice: Double,
    val originalPrice: Double,
    val discountPercent: Int,
    val deliveryDate: String,
    val colorSwatches: List<Long>,
    val salesTag: String,
    val variantLabel: String
)

class StoreViewModel : ViewModel() {

    private val detailRepo = ProductDetailRepositoryImpl()

    private lateinit var brandRepo: BrandRepository

    private val _brand = MutableStateFlow<Brand?>(null)
    val brand: StateFlow<Brand?> = _brand.asStateFlow()

    private val _storeCards = MutableStateFlow<List<StoreProductCard>>(emptyList())
    val storeCards: StateFlow<List<StoreProductCard>> = _storeCards.asStateFlow()

    private val _isFollowing = MutableStateFlow(false)
    val isFollowing: StateFlow<Boolean> = _isFollowing.asStateFlow()

    fun loadStore(context: Context, brandId: String) {
        if (!::brandRepo.isInitialized) {
            brandRepo = BrandRepository.getInstance(context)
        }
        val brand = brandRepo.getById(brandId) ?: return
        _brand.value = brand
        _isFollowing.value = brand.isFollowed

        val cards = mutableListOf<StoreProductCard>()
        for (productId in brand.productIds) {
            val detail = detailRepo.getProductById(productId) ?: continue
            cards.addAll(expandProduct(detail))
        }
        _storeCards.value = cards
    }

    fun toggleFollow() {
        val currentBrand = _brand.value ?: return
        if (::brandRepo.isInitialized) {
            val newFollowed = brandRepo.toggleFollow(currentBrand.brandId)
            _isFollowing.value = newFollowed
            _brand.value = currentBrand.copy(isFollowed = newFollowed)
        }
    }

    private fun expandProduct(detail: ProductDetailData): List<StoreProductCard> {
        // Find the last spec group that has price info
        val expandGroup = detail.specGroups.lastOrNull { group ->
            group.options.any { it.price != null }
        }

        // Collect color swatches from Color dimension
        val colorSwatches = detail.specGroups
            .find { it.dimensionName.equals("Color", ignoreCase = true) }
            ?.options?.mapNotNull { it.placeholderColor }
            ?: emptyList()

        if (expandGroup == null) {
            // No price-bearing dimension, generate single card with base info
            val defaultOption = detail.specGroups.flatMap { it.options }.firstOrNull()
            return listOf(
                StoreProductCard(
                    productId = detail.id,
                    cardTitle = detail.name,
                    imageAssetPath = detail.imageAssetPath,
                    rating = detail.rating,
                    reviewCount = detail.reviewCount,
                    currentPrice = defaultOption?.price ?: 0.0,
                    originalPrice = defaultOption?.originalPrice ?: 0.0,
                    discountPercent = defaultOption?.discountPercent ?: 0,
                    deliveryDate = detail.freeDeliveryDate,
                    colorSwatches = colorSwatches,
                    salesTag = detail.salesTag,
                    variantLabel = ""
                )
            )
        }

        return expandGroup.options.map { option ->
            val baseName = detail.baseName.ifEmpty { detail.name }
            // Collect default labels from other dimensions (non-expanded, multi-option only)
            val otherLabels = detail.specGroups
                .filter { it.dimensionName != expandGroup.dimensionName && it.options.size > 1 }
                .mapNotNull { group ->
                    val defaultId = detail.defaultSpecOptionIds[group.dimensionName]
                    group.options.find { it.id == defaultId }?.label
                }
            val allLabels = otherLabels + option.label
            val fullVariant = allLabels.joinToString(", ")
            StoreProductCard(
                productId = detail.id,
                cardTitle = "$baseName, $fullVariant",
                imageAssetPath = detail.imageAssetPath,
                rating = detail.rating,
                reviewCount = detail.reviewCount,
                currentPrice = option.price ?: 0.0,
                originalPrice = option.originalPrice ?: 0.0,
                discountPercent = option.discountPercent ?: 0,
                deliveryDate = detail.freeDeliveryDate,
                colorSwatches = colorSwatches,
                salesTag = detail.salesTag,
                variantLabel = fullVariant
            )
        }
    }
}
