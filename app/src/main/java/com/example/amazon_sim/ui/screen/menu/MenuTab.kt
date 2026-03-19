package com.example.amazon_sim.ui.screen.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.ui.screen.menu.components.ActionCard
import com.example.amazon_sim.ui.screen.menu.components.DepartmentGroup
import com.example.amazon_sim.ui.screen.menu.components.MenuSearchBar
import com.example.amazon_sim.ui.screen.menu.components.ShortcutsSection
import com.example.amazon_sim.ui.theme.AmazonDividerGray
import com.example.amazon_sim.ui.theme.AmazonProfileTopBackground

@Composable
fun MenuTab(
    uiState: MenuUiState,
    onSearchClick: () -> Unit = {},
    onShortcutClick: (String) -> Unit = {},
    onToggleDepartment: () -> Unit = {},
    onCategoryClick: (String) -> Unit = {},
    onActionClick: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top search bar with orange background
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AmazonProfileTopBackground)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            MenuSearchBar(onSearchClick = onSearchClick)
        }

        // Scrollable content
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            // Your shortcuts
            item {
                ShortcutsSection(
                    shortcuts = uiState.shortcuts,
                    onShortcutClick = onShortcutClick
                )
            }

            // Divider
            item {
                HorizontalDivider(color = AmazonDividerGray, thickness = 1.dp)
            }

            // Shop by category title
            item {
                Text(
                    text = "Shop by category",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
                )
            }

            // Department group (expandable)
            item {
                DepartmentGroup(
                    isExpanded = uiState.isDepartmentExpanded,
                    categories = uiState.categories,
                    onToggle = onToggleDepartment,
                    onCategoryClick = onCategoryClick
                )
            }

            // Spacer
            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Bottom action cards
            item {
                Column {
                    uiState.bottomActions.forEachIndexed { index, action ->
                        ActionCard(
                            title = action,
                            onClick = { onActionClick(action) }
                        )
                        if (index < uiState.bottomActions.lastIndex) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }

            // Spacer
            item { Spacer(modifier = Modifier.height(8.dp)) }

            // Settings hint row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Info",
                        tint = Color(0xFF00897B),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Looking for app settings? They've moved to ",
                        fontSize = 14.sp,
                        color = Color(0xFF555555),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Profile",
                        tint = Color(0xFF555555),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Bottom spacing
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
