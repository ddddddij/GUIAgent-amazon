package com.example.amazon_sim.data

import android.content.Context
import com.example.amazon_sim.ui.screen.cart.CartItemUi
import com.example.amazon_sim.ui.screen.cart.StockStatusType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

object CartManager {

    private const val FILE_NAME = "cart_data.json"

    private val _cartItems = MutableStateFlow<List<CartItemUi>>(emptyList())
    val cartItems: StateFlow<List<CartItemUi>> = _cartItems.asStateFlow()

    private var initialized = false

    fun init(context: Context) {
        if (initialized) return
        initialized = true
        loadFromAssets(context.applicationContext)
    }

    /** 强制从 assets 重新加载初始数据，用于 app 启动时还原 */
    fun reset(context: Context) {
        initialized = true
        loadFromAssets(context.applicationContext)
    }

    private fun loadFromAssets(appContext: Context) {
        val file = File(appContext.filesDir, FILE_NAME)
        file.delete()
        val json = runCatching {
            appContext.assets.open("data/cart_items.json")
                .bufferedReader().use { it.readText() }
        }.getOrDefault("[]")
        _cartItems.value = parseJson(json).map { it.copy(isSelected = false) }
        save(appContext)
    }

    fun addToCart(
        context: Context,
        productName: String,
        price: Double,
        quantity: Int,
        placeholderColor: Long,
        imageAssetPath: String = "",
        productId: String = ""
    ) {
        val existing = _cartItems.value.find { item ->
            if (productId.isNotEmpty()) {
                item.productId == productId && item.price == price
            } else {
                item.productName == productName && item.price == price
            }
        }
        if (existing != null) {
            // Merge: increase quantity and move to top
            val updated = existing.copy(quantity = existing.quantity + quantity)
            _cartItems.value = listOf(updated) + _cartItems.value.filter { it.id != existing.id }
        } else {
            val newId = (_cartItems.value.maxOfOrNull { it.id.toIntOrNull() ?: 0 } ?: 0) + 1
            val newItem = CartItemUi(
                id = newId.toString(),
                productId = productId,
                productName = productName,
                price = price,
                quantity = quantity,
                isSelected = false,
                stockStatus = "In Stock",
                stockStatusType = StockStatusType.IN_STOCK,
                placeholderColor = placeholderColor,
                imageAssetPath = imageAssetPath
            )
            _cartItems.value = listOf(newItem) + _cartItems.value
        }
        save(context.applicationContext)
    }

    fun updateItems(context: Context, items: List<CartItemUi>) {
        _cartItems.value = items
        save(context.applicationContext)
    }

    fun removeItems(context: Context, itemIds: List<String>) {
        _cartItems.value = _cartItems.value.filter { it.id !in itemIds }
        save(context.applicationContext)
    }

    private fun save(context: Context) {
        val array = JSONArray()
        _cartItems.value.forEach { item ->
            array.put(JSONObject().apply {
                put("id", item.id)
                put("productId", item.productId)
                put("productName", item.productName)
                put("price", item.price)
                put("quantity", item.quantity)
                put("isSelected", item.isSelected)
                put("stockStatus", item.stockStatus)
                put("stockStatusType", item.stockStatusType.name)
                put("placeholderColor", "0x${item.placeholderColor.toString(16).uppercase()}")
                put("imageAssetPath", item.imageAssetPath)
                put("showViewInRoom", item.showViewInRoom)
            })
        }
        File(context.filesDir, FILE_NAME).writeText(array.toString(2))
    }

    private fun parseJson(json: String): List<CartItemUi> {
        return runCatching {
            val array = JSONArray(json)
            buildList(array.length()) {
                for (i in 0 until array.length()) {
                    val obj = array.optJSONObject(i) ?: continue
                    add(
                        CartItemUi(
                            id = obj.optString("id"),
                            productId = obj.optString("productId", ""),
                            productName = obj.optString("productName"),
                            price = obj.optDouble("price", 0.0),
                            quantity = obj.optInt("quantity", 1),
                            isSelected = obj.optBoolean("isSelected", false),
                            stockStatus = obj.optString("stockStatus", "In Stock"),
                            stockStatusType = when (obj.optString("stockStatusType", "IN_STOCK")) {
                                "LOW_STOCK" -> StockStatusType.LOW_STOCK
                                "FREE_RETURN" -> StockStatusType.FREE_RETURN
                                else -> StockStatusType.IN_STOCK
                            },
                            placeholderColor = obj.optString("placeholderColor", "0xFFCCCCCC")
                                .removePrefix("0x").toLongOrNull(16) ?: 0xFFCCCCCC,
                            imageAssetPath = obj.optString("imageAssetPath", ""),
                            showViewInRoom = obj.optBoolean("showViewInRoom", false)
                        )
                    )
                }
            }
        }.getOrDefault(emptyList())
    }
}
