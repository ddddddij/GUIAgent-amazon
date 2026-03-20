package com.example.amazon_sim.data.repository

import android.content.Context
import com.example.amazon_sim.domain.model.Product
import com.example.amazon_sim.domain.repository.ProductRepository
import org.json.JSONArray
import org.json.JSONObject

class ProductRepositoryImpl(private val context: Context) : ProductRepository {

    override fun getProducts(): List<Product> {
        return runCatching {
            val json = context.assets.open(ASSET_PATH)
                .bufferedReader()
                .use { it.readText() }
            val array = JSONArray(json)
            buildList(array.length()) {
                for (index in 0 until array.length()) {
                    val item = array.optJSONObject(index) ?: continue
                    add(item.toProduct())
                }
            }
        }.getOrDefault(emptyList())
    }

    private fun JSONObject.toProduct(): Product = Product(
        id = optString("id"),
        name = optString("name"),
        store = optString("store", DEFAULT_STORE).ifBlank { DEFAULT_STORE },
        price = optDouble("price", 0.0),
        imageUrl = optString("imageUrl"),
        category = optString("category"),
        rating = optDouble("rating", 0.0),
        reviewCount = optInt("reviewCount", 0),
        timestamp = optLong("timestamp", 0L)
    )

    private companion object {
        const val ASSET_PATH = "data/products.json"
        const val DEFAULT_STORE = "Amazon.com"
    }
}
