package com.example.amazon_sim.ui.screen.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.ui.screen.account.components.AboutCard
import com.example.amazon_sim.ui.screen.account.components.FunctionCard
import com.example.amazon_sim.ui.screen.account.components.NotificationCard
import com.example.amazon_sim.ui.screen.account.components.SectionTitle
import com.example.amazon_sim.ui.screen.account.components.UserProfileCard

@Composable
fun AccountScreen(
    uiState: AccountUiState,
    onBackClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onLookingForOrdersClick: () -> Unit,
    onPurchaseHistoryClick: () -> Unit,
    onReturnsClick: () -> Unit,
    onYourAddressesClick: () -> Unit,
    onManagePaymentClick: () -> Unit,
    onWalletClick: () -> Unit,
    onNotificationToggle: () -> Unit,
    onPrivacyClick: () -> Unit,
    onHelpClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        // Top bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.White)
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
                Text(
                    text = "Your Account",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings",
                        tint = Color(0xFF888888),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        // User profile card
        item {
            Spacer(modifier = Modifier.height(8.dp))
            UserProfileCard(
                userName = uiState.userName,
                userEmail = uiState.userEmail,
                onEditProfileClick = onEditProfileClick
            )
        }

        // Orders section
        item { SectionTitle(title = "Your Orders") }
        item {
            FunctionCard(
                title = "Looking for Orders",
                onClick = onLookingForOrdersClick
            )
        }
        item {
            FunctionCard(
                title = "Purchase History",
                onClick = onPurchaseHistoryClick
            )
        }
        item {
            FunctionCard(
                title = "Returns & Exchanges",
                onClick = onReturnsClick
            )
        }

        // Address section
        item { SectionTitle(title = "Shipping & Addresses") }
        item {
            FunctionCard(
                title = "Your addresses",
                subtitle = if (uiState.defaultAddressPreview.isNotEmpty())
                    "Default: ${uiState.defaultAddressPreview}" else "",
                onClick = onYourAddressesClick
            )
        }

        // Payment section
        item { SectionTitle(title = "Payment Methods") }
        item {
            FunctionCard(
                title = "Manage payment methods",
                onClick = onManagePaymentClick
            )
        }
        item {
            FunctionCard(
                title = "Wallet & Gift Cards",
                onClick = onWalletClick
            )
        }

        // Settings section
        item { SectionTitle(title = "Settings") }
        item {
            NotificationCard(
                checked = uiState.notificationsEnabled,
                onToggle = onNotificationToggle
            )
        }
        item {
            FunctionCard(
                title = "Privacy & Security",
                onClick = onPrivacyClick
            )
        }
        item {
            FunctionCard(
                title = "Help & Support",
                onClick = onHelpClick
            )
        }
        item { AboutCard() }

        // Bottom spacer
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}
