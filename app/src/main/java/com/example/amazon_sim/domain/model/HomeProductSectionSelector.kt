package com.example.amazon_sim.domain.model

object HomeProductSectionSelector {

    private const val ELECTRONICS_CATEGORY = "Electronics"
    private const val LIFESTYLE_CATEGORY = "Lifestyle"
    private const val SPORTS_CATEGORY = "Sports"
    private const val FOOD_CATEGORY = "Food"
    private const val MAX_ELECTRONICS_COUNT = 6
    private const val RECOMMENDED_COUNT = 6
    private const val KEEP_SHOPPING_COUNT = 4

    fun select(products: List<Product>): HomeProductSections {
        val sortedProducts = products.sortedByDescending(Product::timestamp)

        val electronics = sortedProducts
            .filter { it.category == ELECTRONICS_CATEGORY }
            .take(MAX_ELECTRONICS_COUNT)
        val electronicsIds = electronics.mapTo(mutableSetOf(), Product::id)

        val remainingAfterElectronics = sortedProducts.filterNot { it.id in electronicsIds }
        val recommendedDeals = remainingAfterElectronics
            .filter { it.category in setOf(LIFESTYLE_CATEGORY, FOOD_CATEGORY) }
            .ifEmpty { remainingAfterElectronics }
            .take(RECOMMENDED_COUNT)
        val recommendedIds = recommendedDeals.mapTo(mutableSetOf(), Product::id)

        val remainingAfterRecommended = remainingAfterElectronics.filterNot { it.id in recommendedIds }
        val sportsProducts = remainingAfterRecommended.filter { it.category == SPORTS_CATEGORY }
        val otherRemainingProducts = remainingAfterRecommended.filterNot { it.category == SPORTS_CATEGORY }
        val keepShopping = (sportsProducts + otherRemainingProducts).take(KEEP_SHOPPING_COUNT)

        return HomeProductSections(
            recommendedDeals = recommendedDeals,
            electronics = electronics,
            keepShopping = keepShopping
        )
    }
}
