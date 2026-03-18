package com.example.amazon_sim.domain.model

/**
 * 订单状态枚举
 * 用于支持任务：查看订单列表
 */
enum class OrderStatus {
    PENDING,      // 待支付
    PROCESSING,   // 处理中
    SHIPPED,      // 已发货
    DELIVERED,    // 已送达
    CANCELLED     // 已取消
}
