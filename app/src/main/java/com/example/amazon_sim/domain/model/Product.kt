package com.example.amazon_sim.domain.model

/**
 * 商品基础信息模型
 * 用于支持任务：搜索商品、浏览商品详情、再次购买
 */
data class Product(
    val id: String,            // 商品唯一标识
    val name: String,          // 商品名称
    val store: String,         // 店铺/卖家信息
    val price: Double,         // 商品价格
    val imageUrl: String,      // 商品图片资源路径
    val category: String,      // 商品分类
    val rating: Double,        // 评分
    val reviewCount: Int,      // 评论数
    val timestamp: Long,       // 记录时间戳（用于验证）
    val brandName: String = "",     // 品牌名，用于搜索，如 "Apple", "Marshall"
    val productType: String = "",   // 商品类型，用于搜索，如 "smartphone", "laptop", "speaker"
    val isBestSeller: Boolean = false,  // 是否展示 Best Seller 角标
    val specTags: List<String> = emptyList(), // 搜索结果卡片上展示的规格标签，如 ["Unlocked", "128GB"]
    val colorSwatches: List<Long> = emptyList() // 搜索结果颜色色块色值列表（Color Long），如 [0xFF1A1A1A, 0xFFFFF8E7]
)
