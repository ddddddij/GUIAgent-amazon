package com.example.amazon_sim.domain.model

data class CheckoutItem(
    val productId: String,
    val productName: String,
    val productImageRes: String,  // imageAssetPath
    val variantLabel: String,     // "Black / 128GB"
    val unitPrice: Double,
    val quantity: Int,
    val freeDeliveryDate: String
)
