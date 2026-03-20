package com.example.amazon_sim.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amazon_sim.ui.screen.home.components.HomeBannerSection
import com.example.amazon_sim.ui.screen.home.components.HomeDealsSection
import com.example.amazon_sim.ui.screen.home.components.HomeDevicesGrid
import com.example.amazon_sim.ui.screen.home.components.HomeFreshFindsSection
import com.example.amazon_sim.ui.screen.home.components.HomeKeepShoppingSection
import com.example.amazon_sim.ui.screen.home.components.HomeTopBar
import com.example.amazon_sim.ui.screen.home.components.HomeTrendingGrid

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onSearchClick: () -> Unit = {},
    onProductClick: (String) -> Unit = {},
    onCategoryClick: (String) -> Unit = {},
    onAddressClick: () -> Unit = {}
) {
    val deliveryAddress by viewModel.deliveryAddress.collectAsStateWithLifecycle()
    val quickTags by viewModel.quickTags.collectAsStateWithLifecycle()
    val banners by viewModel.banners.collectAsStateWithLifecycle()
    val recommendedDeals by viewModel.recommendedDeals.collectAsStateWithLifecycle()
    val amazonDevices by viewModel.amazonDevices.collectAsStateWithLifecycle()
    val trendingCategories by viewModel.trendingCategories.collectAsStateWithLifecycle()
    val keepShoppingProducts by viewModel.keepShoppingProducts.collectAsStateWithLifecycle()
    val keepShoppingTitle by viewModel.keepShoppingTitle.collectAsStateWithLifecycle()
    val freshFindsCategories by viewModel.freshFindsCategories.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HomeTopBar(
            deliveryAddress = deliveryAddress,
            quickTags = quickTags,
            onSearchClick = onSearchClick,
            onAddressClick = onAddressClick
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item { HomeBannerSection(banners = banners) }
            item {
                HomeDealsSection(
                    title = "Recommended deals for you",
                    deals = recommendedDeals,
                    onProductClick = onProductClick
                )
            }
            item {
                HomeDevicesGrid(
                    title = "Save on Amazon Devices",
                    devices = amazonDevices,
                    onProductClick = onProductClick
                )
            }
            item {
                HomeTrendingGrid(
                    title = "Trending near you",
                    categories = trendingCategories,
                    onCategoryClick = onCategoryClick
                )
            }
            item {
                HomeKeepShoppingSection(
                    title = keepShoppingTitle,
                    products = keepShoppingProducts,
                    onProductClick = onProductClick
                )
            }
            item {
                HomeFreshFindsSection(
                    title = "Fresh finds just landed",
                    categories = freshFindsCategories,
                    onCategoryClick = onCategoryClick
                )
            }
        }
    }
}
