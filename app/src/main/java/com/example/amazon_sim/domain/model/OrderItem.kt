package com.example.amazon_sim.domain.model

/**
 * 订单项模型
 * 用于订单中的商品项
 */
data class OrderItem(
    val productId: String,                  // 商品ID
    val productName: String,                // 商品名称
    val productImage: String,               // 商品图片
    val price: Double,                      // 单价
    val quantity: Int,                      // 数量
    val selectedSpec: List<ProductSpec>     // 选中的规格
)
