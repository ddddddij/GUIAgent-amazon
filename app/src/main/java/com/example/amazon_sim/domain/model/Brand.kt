package com.example.amazon_sim.domain.model

data class Brand(
    val brandId: String,
    val brandName: String,
    val bannerBgColor: Long,
    val bannerTextColor: Long,
    val productIds: List<String>
)
