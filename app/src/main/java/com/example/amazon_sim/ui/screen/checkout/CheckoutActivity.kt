package com.example.amazon_sim.ui.screen.checkout

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.amazon_sim.data.repository.AddressRepositoryImpl
import com.example.amazon_sim.ui.screen.address.AddressActivity
import com.example.amazon_sim.MainActivity
import com.example.amazon_sim.ui.screen.order.OrderActivity
import com.example.amazon_sim.ui.theme.Amazon_simTheme

class CheckoutActivity : ComponentActivity() {

    private var showSuccessDialog by mutableStateOf(false)

    private val addressLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val viewModel = CheckoutViewModelHolder.get(application)
        val addressRepo = AddressRepositoryImpl(applicationContext)
        val selectedId = result.data?.getStringExtra(AddressActivity.RESULT_SELECTED_ADDRESS_ID)
        if (selectedId != null) {
            val selectedAddr = addressRepo.getAddressById(selectedId)
            if (selectedAddr != null) {
                viewModel.updateDeliveryAddress(selectedAddr)
                return@registerForActivityResult
            }
        }
        // Fallback: reload default address
        val addresses = addressRepo.getAddresses()
        val defaultAddr = addresses.find { it.isDefault } ?: addresses.firstOrNull()
        if (defaultAddr != null) {
            viewModel.updateDeliveryAddress(defaultAddr)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel = CheckoutViewModelHolder.get(application)

        setContent {
            Amazon_simTheme {
                val items by viewModel.checkoutItems.collectAsStateWithLifecycle()
                val summary by viewModel.orderSummary.collectAsStateWithLifecycle()
                val address by viewModel.deliveryAddress.collectAsStateWithLifecycle()
                val selectedMethod by viewModel.selectedPaymentMethod.collectAsStateWithLifecycle()

                CheckoutScreen(
                    items = items,
                    summary = summary,
                    deliveryAddress = address,
                    selectedPaymentMethod = selectedMethod,
                    showSuccessDialog = showSuccessDialog,
                    onBackClick = { finish() },
                    onChangeAddress = {
                        addressLauncher.launch(
                            Intent(this, AddressActivity::class.java).apply {
                                putExtra(AddressActivity.EXTRA_SELECT_MODE, true)
                            }
                        )
                    },
                    onPlaceOrder = {
                        viewModel.confirmOrder()
                        viewModel.removeCartItemsAfterPayment()
                        showSuccessDialog = true
                    },
                    onViewOrders = {
                        showSuccessDialog = false
                        CheckoutViewModelHolder.clear()
                        val intent = Intent(this, OrderActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    },
                    onContinueShopping = {
                        showSuccessDialog = false
                        CheckoutViewModelHolder.clear()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}
