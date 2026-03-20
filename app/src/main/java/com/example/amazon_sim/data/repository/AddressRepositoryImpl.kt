package com.example.amazon_sim.data.repository

import android.content.Context
import com.example.amazon_sim.domain.model.Address
import com.example.amazon_sim.domain.repository.AddressRepository
import org.json.JSONArray
import org.json.JSONObject

class AddressRepositoryImpl(private val context: Context) : AddressRepository {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    init {
        seedAddressesIfNeeded()
    }

    private fun seedAddressesIfNeeded() {
        val currentVersion = prefs.getInt(KEY_SEED_VERSION, 0)
        val storedAddresses = getStoredAddresses()
        if (currentVersion >= ADDRESS_SEED_VERSION && storedAddresses.isNotEmpty()) {
            return
        }

        val assetAddresses = loadAssetAddresses()
        if (assetAddresses.isEmpty()) {
            return
        }

        val mergedAddresses = if (storedAddresses.isEmpty()) {
            assetAddresses
        } else {
            mergeAddresses(storedAddresses, assetAddresses)
        }

        saveAll(mergedAddresses)
        prefs.edit().putInt(KEY_SEED_VERSION, ADDRESS_SEED_VERSION).apply()
    }

    private fun loadAssetAddresses(): List<Address> {
        return runCatching {
            val json = context.assets.open(ASSET_PATH)
                .bufferedReader()
                .use { it.readText() }
            parseAddresses(json)
        }.getOrDefault(emptyList())
    }

    private fun getStoredAddresses(): List<Address> {
        val json = prefs.getString(KEY_ADDRESS_LIST, null) ?: return emptyList()
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

    private fun mergeAddresses(
        storedAddresses: List<Address>,
        assetAddresses: List<Address>
    ): List<Address> {
        val storedById = storedAddresses.associateBy(Address::id)
        val merged = assetAddresses.map { assetAddress ->
            val storedAddress = storedById[assetAddress.id]
            if (storedAddress == null) {
                assetAddress
            } else {
                storedAddress.fillMissingFieldsFrom(assetAddress)
            }
        }.toMutableList()

        storedAddresses
            .filterNot { storedById.containsKey(it.id) && assetAddresses.any { asset -> asset.id == it.id } }
            .forEach(merged::add)

        return ensureSingleDefaultAddress(merged)
    }

    override fun getAddresses(): List<Address> {
        return getStoredAddresses()
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
        prefs.edit().putString(KEY_ADDRESS_LIST, array.toString()).apply()
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

    private fun Address.fillMissingFieldsFrom(fallback: Address): Address {
        return copy(
            fullName = fullName.ifBlank { fallback.fullName },
            phoneNumber = phoneNumber.ifBlank { fallback.phoneNumber },
            country = country.ifBlank { fallback.country },
            streetAddress = streetAddress.ifBlank { fallback.streetAddress },
            aptSuite = aptSuite.ifBlank { fallback.aptSuite },
            city = city.ifBlank { fallback.city },
            state = state.ifBlank { fallback.state },
            zipCode = zipCode.ifBlank { fallback.zipCode },
            isDefault = isDefault || fallback.isDefault
        )
    }

    private companion object {
        const val PREFS_NAME = "addresses"
        const val KEY_ADDRESS_LIST = "address_list"
        const val KEY_SEED_VERSION = "address_seed_version"
        const val ASSET_PATH = "data/addresses.json"
        const val ADDRESS_SEED_VERSION = 1
        const val DEFAULT_COUNTRY = "United States"
        const val DEFAULT_STATE = "California"
    }
}
