package com.example.amazon_sim.domain.model

/**
 * 单个规格选项（对应选择区中的一个格子）
 *
 * - placeholderColor != null 时：渲染带占位色块的卡片（适用于颜色变体等）
 * - placeholderColor == null 时：渲染纯文字圆角格子（适用于存储容量、配置型号等）
 * - price 等价格字段可选：有值时在选项卡片上显示价格，无值时不显示
 */
data class SpecOption(
    val id: String,
    val label: String,
    val placeholderColor: Long? = null,
    val price: Double? = null,
    val originalPrice: Double? = null,
    val discountPercent: Int? = null,
    val stockStatus: String = "In Stock",
    val isInStock: Boolean = true
)

/**
 * 一个规格维度（对应页面上的一行规格选择）
 */
data class SpecGroup(
    val dimensionName: String,
    val options: List<SpecOption>
)
