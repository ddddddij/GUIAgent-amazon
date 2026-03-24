package com.example.amazon_sim.data.repository

import com.example.amazon_sim.domain.model.Brand

object BrandRepository {

    private val brands = listOf(
        Brand(
            brandId = "brand_marshall",
            brandName = "Marshall",
            bannerBgColor = 0xFF1A1A1A,
            bannerTextColor = 0xFFFFFFFF,
            productIds = listOf("prod_003")
        ),
        Brand(
            brandId = "brand_apple",
            brandName = "Apple",
            bannerBgColor = 0xFF000000,
            bannerTextColor = 0xFFFFFFFF,
            productIds = listOf("prod_001", "prod_002")
        ),
        Brand(
            brandId = "brand_sony",
            brandName = "Sony",
            bannerBgColor = 0xFF000000,
            bannerTextColor = 0xFFFFFFFF,
            productIds = listOf("prod_004")
        ),
        Brand(
            brandId = "brand_nintendo",
            brandName = "Nintendo",
            bannerBgColor = 0xFFE4000F,
            bannerTextColor = 0xFFFFFFFF,
            productIds = listOf("prod_005")
        ),
        Brand(
            brandId = "brand_samsung",
            brandName = "Samsung",
            bannerBgColor = 0xFF1428A0,
            bannerTextColor = 0xFFFFFFFF,
            productIds = listOf("prod_006")
        ),
        Brand(
            brandId = "brand_dyson",
            brandName = "Dyson",
            bannerBgColor = 0xFF1A1A1A,
            bannerTextColor = 0xFFFFFFFF,
            productIds = listOf("prod_007")
        ),
        Brand(
            brandId = "brand_nespresso",
            brandName = "Nespresso",
            bannerBgColor = 0xFF1A1A1A,
            bannerTextColor = 0xFFFFFFFF,
            productIds = listOf("prod_008")
        ),
        Brand(
            brandId = "brand_kitchenaid",
            brandName = "KitchenAid",
            bannerBgColor = 0xFF1A1A1A,
            bannerTextColor = 0xFFFFFFFF,
            productIds = listOf("prod_009")
        ),
        Brand(
            brandId = "brand_vitamix",
            brandName = "Vitamix",
            bannerBgColor = 0xFF1A1A1A,
            bannerTextColor = 0xFFFFFFFF,
            productIds = listOf("prod_010")
        ),
        Brand(
            brandId = "brand_nike",
            brandName = "Nike",
            bannerBgColor = 0xFF111111,
            bannerTextColor = 0xFFFFFFFF,
            productIds = listOf("prod_011")
        ),
        Brand(
            brandId = "brand_adidas",
            brandName = "adidas",
            bannerBgColor = 0xFF000000,
            bannerTextColor = 0xFFFFFFFF,
            productIds = listOf("prod_012")
        ),
        Brand(
            brandId = "brand_wilson",
            brandName = "Wilson",
            bannerBgColor = 0xFFCC0000,
            bannerTextColor = 0xFFFFFFFF,
            productIds = listOf("prod_013")
        ),
        Brand(
            brandId = "brand_starbucks",
            brandName = "Starbucks",
            bannerBgColor = 0xFF00704A,
            bannerTextColor = 0xFFFFFFFF,
            productIds = listOf("prod_014")
        ),
        Brand(
            brandId = "brand_cocacola",
            brandName = "Coca-Cola",
            bannerBgColor = 0xFFD32F2F,
            bannerTextColor = 0xFFFFFFFF,
            productIds = listOf("prod_015")
        ),
        Brand(
            brandId = "brand_oreo",
            brandName = "OREO",
            bannerBgColor = 0xFF1565C0,
            bannerTextColor = 0xFFFFFFFF,
            productIds = listOf("prod_016")
        )
    )

    fun getById(brandId: String): Brand? {
        return brands.find { it.brandId == brandId }
    }

    fun getByName(brandName: String): Brand? {
        return brands.find { it.brandName.equals(brandName, ignoreCase = true) }
    }
}
