package com.example.amazon_sim.domain.repository

import com.example.amazon_sim.domain.model.ProductDetailData

interface ProductDetailRepository {
    fun getProductById(productId: String): ProductDetailData?
}
