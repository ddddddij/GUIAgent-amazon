package com.example.amazon_sim.domain.model

data class ProductDetailData(
    val id: String,
    val name: String,
    val brandName: String,
    val rating: Float,
    val reviewCount: Int,
    val globalRatingsCount: Int,
    val salesTag: String,
    val imagePlaceholderColors: List<Long>,
    val specGroups: List<SpecGroup>,
    val defaultSpecOptionIds: Map<String, String>,
    val freeDeliveryDate: String,
    val fastestDeliveryDate: String,
    val countdownText: String,
    val installmentMonthly: Double?,
    val installmentMonths: Int?,
    val reviewSummary: String?,
    val reviewTags: List<ReviewTag> = emptyList(),
    val customerPhotoPlaceholderColors: List<Long> = emptyList(),
    val brandLogoPlaceholderColor: Long = 0xFFAAAAAA,
    val imageAssetPath: String = ""
)
