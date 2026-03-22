package com.example.amazon_sim.domain.repository

import com.example.amazon_sim.domain.model.Order
import com.example.amazon_sim.domain.model.OrderStatus
import kotlinx.coroutines.flow.StateFlow

interface OrderRepository {
    val orders: StateFlow<List<Order>>
    fun getOrderById(orderId: String): Order?
    suspend fun updateOrderStatus(orderId: String, newStatus: OrderStatus)
    suspend fun addOrder(order: Order)
}
