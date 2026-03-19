package com.example.amazon_sim.ui.screen.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    onSearchClick: () -> Unit = {},
    onQuickActionClick: (String) -> Unit = {},
    onSectionHeaderClick: (String) -> Unit = {},
    onActionButtonClick: (String) -> Unit = {},
    onBannerClick: (String) -> Unit = {},
    onProductClick: (String) -> Unit = {},
    onHighlightClick: (String) -> Unit = {},
    onAccountEntryClick: (String) -> Unit = {},
    onNeedHelpClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileTab(
        uiState = uiState,
        onSearchClick = onSearchClick,
        onQuickActionClick = onQuickActionClick,
        onSectionHeaderClick = onSectionHeaderClick,
        onActionButtonClick = onActionButtonClick,
        onBannerClick = onBannerClick,
        onProductClick = onProductClick,
        onHighlightClick = onHighlightClick,
        onAccountEntryClick = onAccountEntryClick,
        onNeedHelpClick = onNeedHelpClick
    )
}
