package com.example.amazon_sim.ui.screen.productdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.amazon_sim.data.repository.AddressRepositoryImpl
import com.example.amazon_sim.data.repository.ProductDetailRepositoryImpl
import com.example.amazon_sim.domain.model.ProductDetailData
import com.example.amazon_sim.domain.model.SpecOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class PriceInfo(
    val price: Double,
    val originalPrice: Double,
    val discountPercent: Int
)

class ProductDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val productDetailRepository = ProductDetailRepositoryImpl()
    private val addressRepository = AddressRepositoryImpl(application)

    private val _product = MutableStateFlow<ProductDetailData?>(null)
    val product: StateFlow<ProductDetailData?> = _product.asStateFlow()

    private val _selectedOptions = MutableStateFlow<Map<String, String>>(emptyMap())
    val selectedOptions: StateFlow<Map<String, String>> = _selectedOptions.asStateFlow()

    private val _quantity = MutableStateFlow(1)
    val quantity: StateFlow<Int> = _quantity.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private val _deliverToText = MutableStateFlow("Select delivery address")
    val deliverToText: StateFlow<String> = _deliverToText.asStateFlow()

    val currentPriceInfo: StateFlow<PriceInfo?> = combine(_product, _selectedOptions) { product, selected ->
        if (product == null) return@combine null
        // Take last spec group that has a selected option with price info
        product.specGroups.lastOrNull { group ->
            val optionId = selected[group.dimensionName]
            group.options.find { it.id == optionId }?.price != null
        }?.let { group ->
            val optionId = selected[group.dimensionName]
            val option = group.options.find { it.id == optionId }
            if (option?.price != null) {
                PriceInfo(
                    price = option.price,
                    originalPrice = option.originalPrice ?: option.price,
                    discountPercent = option.discountPercent ?: 0
                )
            } else null
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun loadProduct(productId: String) {
        val data = productDetailRepository.getProductById(productId) ?: return
        _product.value = data
        _selectedOptions.value = data.defaultSpecOptionIds.toMap()
        loadDefaultAddress()
    }

    fun selectOption(dimensionName: String, optionId: String) {
        _selectedOptions.value = _selectedOptions.value + (dimensionName to optionId)
    }

    fun setQuantity(qty: Int) {
        _quantity.value = qty.coerceIn(1, 10)
    }

    fun toggleFavorite() {
        _isFavorite.value = !_isFavorite.value
    }

    fun getSelectedOption(dimensionName: String): SpecOption? {
        val product = _product.value ?: return null
        val optionId = _selectedOptions.value[dimensionName] ?: return null
        return product.specGroups
            .find { it.dimensionName == dimensionName }
            ?.options
            ?.find { it.id == optionId }
    }

    private fun loadDefaultAddress() {
        val addresses = addressRepository.getAddresses()
        val defaultAddr = addresses.find { it.isDefault } ?: addresses.firstOrNull()
        if (defaultAddr != null) {
            _deliverToText.value = "${defaultAddr.fullName} - ${defaultAddr.city} ${defaultAddr.zipCode}"
        }
    }
}
