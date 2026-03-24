package com.example.amazon_sim.domain.model

/**
 * 待购清单
 */
data class ShoppingList(
    val listId: String,           // 唯一ID，如 "list_001"
    val listName: String,         // 清单名称，如 "Shopping List"
    val createdAt: Long,          // 创建时间戳（用于排序）
    val productIds: List<String>  // 清单内商品ID列表，有序
)
