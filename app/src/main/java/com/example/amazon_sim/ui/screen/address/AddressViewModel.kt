package com.example.amazon_sim.ui.screen.address

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.amazon_sim.data.repository.AddressRepositoryImpl
import com.example.amazon_sim.domain.model.Address
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

data class AddressFormState(
    val id: String = "",
    val fullName: String = "",
    val phoneNumber: String = "",
    val country: String = "United States",
    val streetAddress: String = "",
    val aptSuite: String = "",
    val city: String = "",
    val state: String = "California",
    val zipCode: String = "",
    val isDefault: Boolean = false,
    val isEditMode: Boolean = false,
    val errors: Map<String, String> = emptyMap()
)

class AddressViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AddressRepositoryImpl(application)

    private val _addresses = MutableStateFlow<List<Address>>(emptyList())
    val addresses: StateFlow<List<Address>> = _addresses.asStateFlow()

    private val _formState = MutableStateFlow(AddressFormState())
    val formState: StateFlow<AddressFormState> = _formState.asStateFlow()

    init {
        loadAddresses()
    }

    fun loadAddresses() {
        _addresses.value = repository.getAddresses()
    }

    fun deleteAddress(id: String) {
        repository.deleteAddress(id)
        loadAddresses()
    }

    fun loadFormForEdit(addressId: String) {
        val address = repository.getAddressById(addressId) ?: return
        _formState.value = AddressFormState(
            id = address.id,
            fullName = address.fullName,
            phoneNumber = address.phoneNumber,
            country = address.country,
            streetAddress = address.streetAddress,
            aptSuite = address.aptSuite,
            city = address.city,
            state = address.state,
            zipCode = address.zipCode,
            isDefault = address.isDefault,
            isEditMode = true
        )
    }

    fun resetForm() {
        _formState.value = AddressFormState()
    }

    fun updateFormField(field: String, value: String) {
        val current = _formState.value
        _formState.value = when (field) {
            "fullName" -> current.copy(fullName = value, errors = current.errors - field)
            "phoneNumber" -> current.copy(phoneNumber = value, errors = current.errors - field)
            "country" -> current.copy(country = value, errors = current.errors - field)
            "streetAddress" -> current.copy(streetAddress = value, errors = current.errors - field)
            "aptSuite" -> current.copy(aptSuite = value, errors = current.errors - field)
            "city" -> current.copy(city = value, errors = current.errors - field)
            "state" -> current.copy(state = value, errors = current.errors - field)
            "zipCode" -> current.copy(zipCode = value, errors = current.errors - field)
            else -> current
        }
    }

    fun toggleDefault() {
        _formState.value = _formState.value.copy(isDefault = !_formState.value.isDefault)
    }

    fun validateAndSave(): Boolean {
        val form = _formState.value
        val errors = mutableMapOf<String, String>()

        if (form.fullName.isBlank()) errors["fullName"] = "Full name is required"
        if (form.streetAddress.isBlank()) errors["streetAddress"] = "Street address is required"
        if (form.city.isBlank()) errors["city"] = "City is required"
        if (form.zipCode.isBlank()) errors["zipCode"] = "ZIP code is required"

        if (errors.isNotEmpty()) {
            _formState.value = form.copy(errors = errors)
            return false
        }

        val address = Address(
            id = if (form.isEditMode) form.id else UUID.randomUUID().toString(),
            fullName = form.fullName,
            phoneNumber = form.phoneNumber,
            country = form.country,
            streetAddress = form.streetAddress,
            aptSuite = form.aptSuite,
            city = form.city,
            state = form.state,
            zipCode = form.zipCode,
            isDefault = form.isDefault
        )
        repository.saveAddress(address)
        loadAddresses()
        return true
    }
}
