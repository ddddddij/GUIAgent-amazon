package com.example.amazon_sim.data.repository

import android.content.Context
import com.example.amazon_sim.domain.model.Address
import com.example.amazon_sim.domain.model.Order
import com.example.amazon_sim.domain.model.OrderItem
import com.example.amazon_sim.domain.model.OrderStatus
import com.example.amazon_sim.domain.model.ProductSpec
import com.example.amazon_sim.domain.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class OrderRepositoryImpl private constructor(private val context: Context) : OrderRepository {

    private val dataFile = File(context.filesDir, FILE_NAME)

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    override val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    init {
        resetFromAssets()
    }

    /** 每次初始化都从 assets 复制初始数据到 internal files，保证重启还原 */
    private fun resetFromAssets() {
        val json = runCatching {
            context.assets.open(ASSET_PATH)
                .bufferedReader()
                .use { it.readText() }
        }.getOrDefault("[]")
        dataFile.writeText(json)
        _orders.value = parseOrdersFromJson(json)
    }

    private fun parseOrdersFromJson(json: String): List<Order> {
        return runCatching {
            val array = JSONArray(json)
            buildList(array.length()) {
                for (index in 0 until array.length()) {
                    val item = array.optJSONObject(index) ?: continue
                    add(item.toOrder())
                }
            }
        }.getOrDefault(emptyList())
    }

    override fun getOrderById(orderId: String): Order? {
        return _orders.value.find { it.orderId == orderId }
    }

    override suspend fun updateOrderStatus(orderId: String, newStatus: OrderStatus) {
        val updatedOrders = _orders.value.map { order ->
            if (order.orderId == orderId) {
                order.copy(orderStatus = newStatus, updatedAt = System.currentTimeMillis())
            } else {
                order
            }
        }
        _orders.value = updatedOrders
        saveOrders(updatedOrders)
    }

    override suspend fun addOrder(order: Order) {
        val updatedOrders = _orders.value + order
        _orders.value = updatedOrders
        saveOrders(updatedOrders)
    }

    private fun saveOrders(orders: List<Order>) {
        val jsonArray = JSONArray()
        orders.forEach { order ->
            jsonArray.put(order.toJson())
        }
        dataFile.writeText(jsonArray.toString(2))
    }

    private fun Order.toJson(): JSONObject = JSONObject().apply {
        put("orderId", orderId)
        put("items", JSONArray().apply {
            items.forEach { put(it.toJson()) }
        })
        put("totalAmount", totalAmount)
        put("shippingAddress", shippingAddress.toJson())
        put("orderStatus", orderStatus.name)
        put("paymentMethod", paymentMethod)
        put("createdAt", createdAt)
        put("updatedAt", updatedAt)
    }

    private fun OrderItem.toJson(): JSONObject = JSONObject().apply {
        put("productId", productId)
        put("productName", productName)
        put("productImage", productImage)
        put("price", price)
        put("quantity", quantity)
        put("selectedSpec", JSONArray().apply {
            selectedSpec.forEach { put(it.toJson()) }
        })
    }

    private fun ProductSpec.toJson(): JSONObject = JSONObject().apply {
        put("specType", specType)
        put("specValue", specValue)
        put("isSelected", isSelected)
    }

    private fun Address.toJson(): JSONObject = JSONObject().apply {
        put("id", id)
        put("fullName", fullName)
        put("phoneNumber", phoneNumber)
        put("country", country)
        put("streetAddress", streetAddress)
        put("aptSuite", aptSuite)
        put("city", city)
        put("state", state)
        put("zipCode", zipCode)
        put("isDefault", isDefault)
    }

    private fun JSONObject.toOrder(): Order = Order(
        orderId = optString("orderId"),
        items = optJSONArray("items")?.let { arr ->
            (0 until arr.length()).mapNotNull { arr.optJSONObject(it)?.toOrderItem() }
        } ?: emptyList(),
        totalAmount = optDouble("totalAmount", 0.0),
        shippingAddress = optJSONObject("shippingAddress")?.toAddress()
            ?: Address(id = "", fullName = "", phoneNumber = "", streetAddress = "", city = "", zipCode = ""),
        orderStatus = optString("orderStatus").toOrderStatus(),
        paymentMethod = optString("paymentMethod", ""),
        createdAt = optLong("createdAt", 0L),
        updatedAt = optLong("updatedAt", 0L)
    )

    private fun JSONObject.toOrderItem(): OrderItem = OrderItem(
        productId = optString("productId"),
        productName = optString("productName"),
        productImage = optString("productImage"),
        price = optDouble("price", 0.0),
        quantity = optInt("quantity", 1),
        selectedSpec = optJSONArray("selectedSpec")?.let { arr ->
            (0 until arr.length()).mapNotNull { arr.optJSONObject(it)?.toProductSpec() }
        } ?: emptyList()
    )

    private fun JSONObject.toProductSpec(): ProductSpec = ProductSpec(
        specType = optString("specType"),
        specValue = optString("specValue"),
        isSelected = optBoolean("isSelected", false)
    )

    private fun JSONObject.toAddress(): Address = Address(
        id = optString("id"),
        fullName = optString("fullName", optString("recipientName", "")),
        phoneNumber = optString("phoneNumber"),
        country = optString("country", "United States"),
        streetAddress = optString("streetAddress", optString("detailAddress", "")),
        aptSuite = optString("aptSuite", optString("district", "")),
        city = optString("city"),
        state = optString("state", optString("province", "California")),
        zipCode = optString("zipCode", ""),
        isDefault = optBoolean("isDefault", false)
    )

    private fun String.toOrderStatus(): OrderStatus = when (this) {
        "PENDING" -> OrderStatus.PENDING
        "UNSHIPPED" -> OrderStatus.UNSHIPPED
        "SHIPPED" -> OrderStatus.SHIPPED
        "DELIVERED", "RECEIVED" -> OrderStatus.DELIVERED
        "CANCELED" -> OrderStatus.CANCELED
        else -> OrderStatus.PENDING
    }

    companion object {
        private const val ASSET_PATH = "data/orders.json"
        private const val FILE_NAME = "orders.json"

        @Volatile
        private var instance: OrderRepositoryImpl? = null

        fun getInstance(context: Context): OrderRepositoryImpl {
            return instance ?: synchronized(this) {
                instance ?: OrderRepositoryImpl(context.applicationContext).also { instance = it }
            }
        }

        /** 在 Application/Activity 启动时调用，强制重置数据 */
        fun resetInstance() {
            synchronized(this) {
                instance?.resetFromAssets()
            }
        }
    }
}
