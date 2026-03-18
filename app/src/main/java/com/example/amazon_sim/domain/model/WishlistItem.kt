package com.example.amazon_sim.domain.model

/**
 * 购物清单项模型
 * 用于支持任务：加入购物清单
 */
data class WishlistItem(
    val id: String,            // 清单项ID
    val productId: String,     // 商品ID
    val productName: String,   // 商品名称
    val productImage: String,  // 商品图片
    val price: Double,         // 价格
    val addedAt: Long          // 加入时间戳
)
