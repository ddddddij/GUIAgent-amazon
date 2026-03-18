package com.example.amazon_sim.domain.model

/**
 * 收藏项模型
 * 用于支持任务：收藏商品
 */
data class FavoriteItem(
    val id: String,            // 收藏项ID
    val productId: String,     // 商品ID
    val productName: String,   // 商品名称
    val productImage: String,  // 商品图片
    val price: Double,         // 价格
    val favoritedAt: Long      // 收藏时间戳
)
