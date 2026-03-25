package com.example.amazon_sim.ui.screen.account

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amazon_sim.ui.screen.address.AddressActivity
import com.example.amazon_sim.ui.screen.order.OrderActivity
import com.example.amazon_sim.ui.theme.Amazon_simTheme

class AccountActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Amazon_simTheme {
                val viewModel: AccountViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                AccountScreen(
                    uiState = uiState,
                    onBackClick = { finish() },
                    onEditProfileClick = { },
                    onLookingForOrdersClick = {
                        startActivity(Intent(this, OrderActivity::class.java))
                    },
                    onPurchaseHistoryClick = {
                        startActivity(Intent(this, OrderActivity::class.java))
                    },
                    onReturnsClick = { },
                    onYourAddressesClick = {
                        startActivity(Intent(this, AddressActivity::class.java))
                    },
                    onManagePaymentClick = { },
                    onWalletClick = { },
                    onNotificationToggle = { viewModel.toggleNotifications() },
                    onPrivacyClick = { },
                    onHelpClick = { }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh address preview when returning from AddressActivity
    }
}
