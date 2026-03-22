package com.example.amazon_sim.domain.model

/**
 * 订单状态枚举
 * 用于支持任务：查看订单列表
 */
enum class OrderStatus {
    PENDING,      // 等待中（未付款）
    UNSHIPPED,    // 未发货（已付款但未发货）
    SHIPPED,      // 已发货
    DELIVERED,    // 已收货
    CANCELED      // 已取消
}
