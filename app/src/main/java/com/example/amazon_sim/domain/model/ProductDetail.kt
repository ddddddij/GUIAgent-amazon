package com.example.amazon_sim.domain.model

/**
 * 商品详情模型
 * 用于支持任务：浏览商品详情、选择商品规格
 */
data class ProductDetail(
    val productId: String,                  // 关联商品ID
    val description: String,                // 商品描述
    val specifications: List<ProductSpec>,  // 商品规格列表
    val stock: Int,                         // 库存数量
    val seller: String,                     // 卖家信息
    val viewedAt: Long                      // 浏览时间戳
)
