package com.example.amazon_sim.domain.repository

import com.example.amazon_sim.domain.model.Product

interface ProductRepository {
    fun getProducts(): List<Product>
}
