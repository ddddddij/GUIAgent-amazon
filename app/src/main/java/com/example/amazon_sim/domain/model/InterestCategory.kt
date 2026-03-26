package com.example.amazon_sim.domain.model

data class InterestCategory(
    val id: String,
    val displayName: String,
    val imageAssetPath: String?,
    val productTypes: List<String>
)
