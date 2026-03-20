package com.example.amazon_sim.domain.model

data class HomeProductSections(
    val recommendedDeals: List<Product>,
    val electronics: List<Product>,
    val keepShopping: List<Product>
)
