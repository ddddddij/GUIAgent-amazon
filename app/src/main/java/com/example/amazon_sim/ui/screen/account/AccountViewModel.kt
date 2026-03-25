package com.example.amazon_sim.ui.screen.account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.amazon_sim.data.repository.AddressRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AccountUiState(
    val userName: String = "Jiayi Dai",
    val userEmail: String = "daijiayi@example.com",
    val defaultAddressPreview: String = "",
    val notificationsEnabled: Boolean = true
)

class AccountViewModel(application: Application) : AndroidViewModel(application) {

    private val addressRepository = AddressRepositoryImpl(application)

    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    init {
        loadDefaultAddress()
    }

    private fun loadDefaultAddress() {
        val addresses = addressRepository.getAddresses()
        val defaultAddr = addresses.find { it.isDefault } ?: addresses.firstOrNull()
        val preview = if (defaultAddr != null) {
            val full = "${defaultAddr.streetAddress}, ${defaultAddr.city}"
            if (full.length > 20) full.take(20) + "..." else full
        } else ""
        _uiState.value = _uiState.value.copy(defaultAddressPreview = preview)
    }

    fun toggleNotifications() {
        _uiState.value = _uiState.value.copy(
            notificationsEnabled = !_uiState.value.notificationsEnabled
        )
    }

    fun refreshAddress() {
        loadDefaultAddress()
    }
}
