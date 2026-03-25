package com.example.amazon_sim.ui.screen.checkout

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.amazon_sim.data.CartManager
import com.example.amazon_sim.data.repository.AddressRepositoryImpl
import com.example.amazon_sim.data.repository.OrderRepositoryImpl
import com.example.amazon_sim.domain.model.Address
import com.example.amazon_sim.domain.model.CheckoutItem
import com.example.amazon_sim.domain.model.Order
import com.example.amazon_sim.domain.model.OrderItem
import com.example.amazon_sim.domain.model.OrderStatus
import com.example.amazon_sim.domain.model.PaymentMethod
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import java.util.UUID

data class OrderSummary(
    val itemsTotal: Double,
    val shipping: Double = 6.99,
    val tax: Double,
    val orderTotal: Double
)

class CheckoutViewModel(application: Application) : AndroidViewModel(application) {

    private val orderRepository = OrderRepositoryImpl.getInstance(application)
    private val addressRepository = AddressRepositoryImpl(application)

    private val _checkoutItems = MutableStateFlow<List<CheckoutItem>>(emptyList())
    val checkoutItems: StateFlow<List<CheckoutItem>> = _checkoutItems.asStateFlow()

    private val _selectedPaymentMethod = MutableStateFlow<PaymentMethod?>(null)
    val selectedPaymentMethod: StateFlow<PaymentMethod?> = _selectedPaymentMethod.asStateFlow()

    private val _deliveryAddress = MutableStateFlow<Address?>(null)
    val deliveryAddress: StateFlow<Address?> = _deliveryAddress.asStateFlow()

    private val _pendingOrderId = MutableStateFlow<String?>(null)
    val pendingOrderId: StateFlow<String?> = _pendingOrderId.asStateFlow()

    var fromCart: Boolean = false
        private set
    var cartItemIds: List<String> = emptyList()
        private set

    val orderSummary: StateFlow<OrderSummary> = _checkoutItems.map { items ->
        val itemsTotal = items.sumOf { it.unitPrice * it.quantity }
        val shipping = 0.0
        val tax = 0.0
        val orderTotal = itemsTotal
        OrderSummary(
            itemsTotal = itemsTotal,
            shipping = shipping,
            tax = tax,
            orderTotal = orderTotal
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, OrderSummary(0.0, 0.0, 0.0, 0.0))

    init {
        loadDefaultAddress()
    }

    fun setCheckoutItems(items: List<CheckoutItem>, fromCart: Boolean, cartIds: List<String>) {
        _checkoutItems.value = items
        this.fromCart = fromCart
        this.cartItemIds = cartIds
    }

    fun selectPaymentMethod(method: PaymentMethod) {
        _selectedPaymentMethod.value = method
    }

    fun createOrUpdatePendingOrder(): String {
        val existingId = _pendingOrderId.value
        if (existingId != null) {
            return existingId
        }

        val orderId = "ORD-${UUID.randomUUID().toString().take(8).uppercase()}"
        val items = _checkoutItems.value
        val summary = orderSummary.value
        val address = _deliveryAddress.value ?: Address(
            id = "", fullName = "", phoneNumber = "",
            streetAddress = "", city = "", zipCode = ""
        )
        val paymentLabel = when (_selectedPaymentMethod.value) {
            PaymentMethod.CREDIT_DEBIT_CARD -> "Credit Card"
            PaymentMethod.AMAZON_GIFT_CARD -> "Gift Card"
            else -> ""
        }

        val now = System.currentTimeMillis()
        val order = Order(
            orderId = orderId,
            items = items.map { item ->
                OrderItem(
                    productId = item.productId,
                    productName = item.productName,
                    productImage = item.productImageRes,
                    price = item.unitPrice,
                    quantity = item.quantity,
                    selectedSpec = emptyList()
                )
            },
            totalAmount = summary.orderTotal,
            shippingAddress = address,
            orderStatus = OrderStatus.PENDING,
            paymentMethod = paymentLabel,
            createdAt = now,
            updatedAt = now,
            itemsTotal = summary.itemsTotal,
            shipping = summary.shipping,
            tax = summary.tax,
            orderTotal = summary.orderTotal
        )

        runBlocking {
            orderRepository.addOrder(order)
        }
        _pendingOrderId.value = orderId
        return orderId
    }

    fun confirmOrder() {
        val orderId = _pendingOrderId.value ?: return
        runBlocking {
            val existingOrder = orderRepository.getOrderById(orderId) ?: return@runBlocking
            val currentAddress = _deliveryAddress.value
            val updatedOrder = if (currentAddress != null) {
                existingOrder.copy(
                    orderStatus = OrderStatus.UNSHIPPED,
                    shippingAddress = currentAddress,
                    updatedAt = System.currentTimeMillis()
                )
            } else {
                existingOrder.copy(
                    orderStatus = OrderStatus.UNSHIPPED,
                    updatedAt = System.currentTimeMillis()
                )
            }
            orderRepository.updateOrder(updatedOrder)
        }
    }

    fun updateDeliveryAddress(address: Address) {
        _deliveryAddress.value = address
    }

    fun removeCartItemsAfterPayment() {
        if (fromCart && cartItemIds.isNotEmpty()) {
            CartManager.removeItems(getApplication(), cartItemIds)
        }
    }

    fun reset() {
        _checkoutItems.value = emptyList()
        _selectedPaymentMethod.value = null
        _pendingOrderId.value = null
        fromCart = false
        cartItemIds = emptyList()
        loadDefaultAddress()
    }

    private fun loadDefaultAddress() {
        val addresses = addressRepository.getAddresses()
        _deliveryAddress.value = addresses.find { it.isDefault } ?: addresses.firstOrNull()
    }
}

object CheckoutViewModelHolder {
    private var viewModel: CheckoutViewModel? = null

    fun get(application: Application): CheckoutViewModel {
        return viewModel ?: CheckoutViewModel(application).also { viewModel = it }
    }

    fun clear() {
        viewModel?.reset()
        viewModel = null
    }
}
