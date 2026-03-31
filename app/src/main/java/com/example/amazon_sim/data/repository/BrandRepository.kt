package com.example.amazon_sim.data.repository

import android.content.Context
import com.example.amazon_sim.domain.model.Brand
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class BrandRepository(private val context: Context) {

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

    private fun loadBrands(): List<Brand> {
        val json = runCatching { dataFile.readText() }.getOrDefault("[]")
        return parseBrands(json)
    }

    private fun parseBrands(json: String): List<Brand> {
        return runCatching {
            val array = JSONArray(json)
            List(array.length()) { index ->
                array.getJSONObject(index).toBrand()
            }
        }.getOrDefault(emptyList())
    }

    fun getById(brandId: String): Brand? {
        return loadBrands().find { it.brandId == brandId }
    }

    fun getByName(brandName: String): Brand? {
        return loadBrands().find { it.brandName.equals(brandName, ignoreCase = true) }
    }

    fun getAll(): List<Brand> {
        return loadBrands()
    }

    fun toggleFollow(brandId: String): Boolean {
        val brands = loadBrands().toMutableList()
        val index = brands.indexOfFirst { it.brandId == brandId }
        if (index < 0) return false
        val updated = brands[index].copy(isFollowed = !brands[index].isFollowed)
        brands[index] = updated
        saveAll(brands)
        return updated.isFollowed
    }

    private fun saveAll(brands: List<Brand>) {
        val array = JSONArray()
        brands.forEach { array.put(it.toJson()) }
        dataFile.writeText(array.toString(2))
    }

    private fun Brand.toJson(): JSONObject = JSONObject().apply {
        put("brandId", brandId)
        put("brandName", brandName)
        put("bannerBgColor", java.lang.Long.toHexString(bannerBgColor).uppercase())
        put("bannerTextColor", java.lang.Long.toHexString(bannerTextColor).uppercase())
        put("productIds", JSONArray(productIds))
        put("isFollowed", isFollowed)
    }

    private fun JSONObject.toBrand(): Brand = Brand(
        brandId = optString("brandId"),
        brandName = optString("brandName"),
        bannerBgColor = optString("bannerBgColor").toLongOrNull(16) ?: 0xFF1A1A1AL,
        bannerTextColor = optString("bannerTextColor").toLongOrNull(16) ?: 0xFFFFFFFFL,
        productIds = run {
            val arr = optJSONArray("productIds") ?: return@run emptyList()
            List(arr.length()) { arr.getString(it) }
        },
        isFollowed = optBoolean("isFollowed", false)
    )

    private companion object {
        const val ASSET_PATH = "data/brands.json"
        const val FILE_NAME = "brands.json"
    }
}
