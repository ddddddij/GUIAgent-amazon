package com.example.amazon_sim.ui.screen.menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MenuScreen(
    viewModel: MenuViewModel = viewModel(),
    onSearchClick: () -> Unit = {},
    onShortcutClick: (String) -> Unit = {},
    onCategoryClick: (String) -> Unit = {},
    onActionClick: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MenuTab(
        uiState = uiState,
        onSearchClick = onSearchClick,
        onShortcutClick = onShortcutClick,
        onToggleDepartment = { viewModel.toggleDepartmentExpanded() },
        onCategoryClick = onCategoryClick,
        onActionClick = onActionClick
    )
}
