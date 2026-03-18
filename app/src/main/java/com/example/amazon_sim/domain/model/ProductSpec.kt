package com.example.amazon_sim.domain.model

/**
 * 商品规格模型
 * 用于支持任务：选择商品规格
 */
data class ProductSpec(
    val specType: String,      // 规格类型（如：颜色、尺寸）
    val specValue: String,     // 规格值
    val isSelected: Boolean    // 是否选中
)
