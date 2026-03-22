package com.example.amazon_sim.domain.model

/**
 * 订单模型
 * 用于支持任务：提交订单、查看订单列表、再次购买
 */
data class Order(
    val orderId: String,              // 订单ID
    val items: List<OrderItem>,       // 订单项列表
    val totalAmount: Double,          // 总金额
    val shippingAddress: Address,     // 收货地址
    val orderStatus: OrderStatus,     // 订单状态
    val paymentMethod: String,        // 付款方式，如 "Credit Card", "Debit Card", "Amazon Pay", "Gift Card"
    val createdAt: Long,              // 创建时间戳
    val updatedAt: Long               // 更新时间戳
)
