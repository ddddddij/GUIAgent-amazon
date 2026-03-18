package com.example.amazon_sim.domain.model

/**
 * 购物车项模型
 * 用于支持任务：加入购物车、查看购物车、发起结算
 */
data class CartItem(
    val id: String,                         // 购物车项ID
    val productId: String,                  // 商品ID
    val productName: String,                // 商品名称
    val productImage: String,               // 商品图片
    val price: Double,                      // 单价
    val quantity: Int,                      // 数量
    val selectedSpec: List<ProductSpec>,    // 选中的规格
    val addedAt: Long,                      // 加入时间戳
    val isSelected: Boolean                 // 是否选中（用于结算）
)
