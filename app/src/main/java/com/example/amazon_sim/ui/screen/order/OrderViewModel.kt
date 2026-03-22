package com.example.amazon_sim.ui.screen.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.amazon_sim.data.repository.OrderRepositoryImpl
import com.example.amazon_sim.domain.model.Order
import com.example.amazon_sim.domain.model.OrderStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val orderRepository = OrderRepositoryImpl.getInstance(application)

    private val _currentFilter = MutableStateFlow<OrderStatus?>(null)
    val currentFilter: StateFlow<OrderStatus?> = _currentFilter.asStateFlow()

    val filteredOrders: StateFlow<List<Order>> = combine(orderRepository.orders, _currentFilter) { orders, filter ->
        if (filter == null) orders else orders.filter { it.orderStatus == filter }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setFilter(status: OrderStatus?) {
        _currentFilter.value = status
    }
}
