package com.example.amazon_sim.ui.screen.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.amazon_sim.data.repository.OrderRepositoryImpl
import com.example.amazon_sim.domain.model.Order
import com.example.amazon_sim.domain.model.OrderStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val orderRepository = OrderRepositoryImpl.getInstance(application)

    private val _currentOrder = MutableStateFlow<Order?>(null)
    val currentOrder: StateFlow<Order?> = _currentOrder.asStateFlow()

    fun loadOrder(orderId: String) {
        _currentOrder.value = orderRepository.getOrderById(orderId)
    }

    fun cancelOrder() {
        val order = _currentOrder.value ?: return
        viewModelScope.launch {
            orderRepository.updateOrderStatus(order.orderId, OrderStatus.CANCELED)
            _currentOrder.value = orderRepository.getOrderById(order.orderId)
        }
    }

    fun confirmReceipt() {
        val order = _currentOrder.value ?: return
        viewModelScope.launch {
            orderRepository.updateOrderStatus(order.orderId, OrderStatus.DELIVERED)
            _currentOrder.value = orderRepository.getOrderById(order.orderId)
        }
    }
}
