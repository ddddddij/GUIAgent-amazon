package com.example.amazon_sim.domain.repository

import com.example.amazon_sim.domain.model.Address

interface AddressRepository {
    fun getAddresses(): List<Address>
    fun getAddressById(id: String): Address?
    fun saveAddress(address: Address)
    fun deleteAddress(id: String)
    fun setDefaultAddress(id: String)
}
