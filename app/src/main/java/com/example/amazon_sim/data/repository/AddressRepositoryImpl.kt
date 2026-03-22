package com.example.amazon_sim.data.repository

import android.content.Context
import com.example.amazon_sim.domain.model.Address
import com.example.amazon_sim.domain.repository.AddressRepository
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class AddressRepositoryImpl(private val context: Context) : AddressRepository {

    private val dataFile = File(context.filesDir, FILE_NAME)

    init {
        resetFromAssets()
    }

    /** 每次初始化都从 assets 复制初始数据到 internal files，保证重启还原 */
    private fun resetFromAssets() {
        val json = runCatching {
            context.assets.open(ASSET_PATH)
                .bufferedReader()
                .use { it.readText() }
        }.getOrDefault("[]")
        dataFile.writeText(json)
    }

    private fun loadAddresses(): List<Address> {
        val json = runCatching { dataFile.readText() }.getOrDefault("[]")
        return parseAddresses(json)
    }

    private fun parseAddresses(json: String): List<Address> {
        return runCatching {
            val array = JSONArray(json)
            List(array.length()) { index ->
                array.getJSONObject(index).toAddress()
            }
        }.getOrDefault(emptyList())
    }

    override fun getAddresses(): List<Address> {
        return loadAddresses()
    }

    override fun getAddressById(id: String): Address? {
        return getAddresses().find { it.id == id }
    }

    override fun saveAddress(address: Address) {
        val addresses = getAddresses().toMutableList()
        val index = addresses.indexOfFirst { it.id == address.id }
        if (index >= 0) {
            addresses[index] = address
        } else {
            addresses.add(address)
        }
        if (address.isDefault) {
            for (i in addresses.indices) {
                if (addresses[i].id != address.id && addresses[i].isDefault) {
                    addresses[i] = addresses[i].copy(isDefault = false)
                }
            }
        }
        saveAll(ensureSingleDefaultAddress(addresses))
    }

    override fun deleteAddress(id: String) {
        val addresses = getAddresses().toMutableList()
        val removed = addresses.find { it.id == id }
        addresses.removeAll { it.id == id }
        if (removed?.isDefault == true && addresses.isNotEmpty()) {
            addresses[0] = addresses[0].copy(isDefault = true)
        }
        saveAll(ensureSingleDefaultAddress(addresses))
    }

    override fun setDefaultAddress(id: String) {
        val addresses = getAddresses().map { addr ->
            addr.copy(isDefault = addr.id == id)
        }
        saveAll(ensureSingleDefaultAddress(addresses))
    }

    private fun saveAll(addresses: List<Address>) {
        val array = JSONArray()
        addresses.forEach { array.put(it.toJson()) }
        dataFile.writeText(array.toString(2))
    }

    private fun ensureSingleDefaultAddress(addresses: List<Address>): List<Address> {
        if (addresses.isEmpty()) {
            return emptyList()
        }

        val defaultIndex = addresses.indexOfFirst { it.isDefault }
        return addresses.mapIndexed { index, address ->
            when {
                defaultIndex == -1 -> address.copy(isDefault = index == 0)
                index == defaultIndex -> address.copy(isDefault = true)
                address.isDefault -> address.copy(isDefault = false)
                else -> address
            }
        }
    }

    private fun Address.toJson(): JSONObject = JSONObject().apply {
        put("id", id)
        put("fullName", fullName)
        put("phoneNumber", phoneNumber)
        put("country", country)
        put("streetAddress", streetAddress)
        put("aptSuite", aptSuite)
        put("city", city)
        put("state", state)
        put("zipCode", zipCode)
        put("isDefault", isDefault)
    }

    private fun JSONObject.toAddress(): Address = Address(
        id = optString("id"),
        fullName = firstNonBlank("fullName", "recipientName"),
        phoneNumber = optString("phoneNumber", ""),
        country = optString("country", DEFAULT_COUNTRY).ifBlank { DEFAULT_COUNTRY },
        streetAddress = firstNonBlank("streetAddress", "detailAddress"),
        aptSuite = firstNonBlank("aptSuite", "district"),
        city = optString("city", ""),
        state = firstNonBlank("state", "province").ifBlank { DEFAULT_STATE },
        zipCode = optString("zipCode", ""),
        isDefault = optBoolean("isDefault", false)
    )

    private fun JSONObject.firstNonBlank(vararg keys: String): String {
        return keys.firstNotNullOfOrNull { key ->
            optString(key).takeIf { it.isNotBlank() }
        }.orEmpty()
    }

    private companion object {
        const val ASSET_PATH = "data/addresses.json"
        const val FILE_NAME = "addresses.json"
        const val DEFAULT_COUNTRY = "United States"
        const val DEFAULT_STATE = "California"
    }
}
