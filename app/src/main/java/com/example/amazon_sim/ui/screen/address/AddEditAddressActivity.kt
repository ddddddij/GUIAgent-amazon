package com.example.amazon_sim.ui.screen.address

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amazon_sim.ui.theme.Amazon_simTheme

class AddEditAddressActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val addressId = intent.getStringExtra("address_id")

        setContent {
            Amazon_simTheme {
                val viewModel: AddressViewModel = viewModel()

                LaunchedEffect(addressId) {
                    if (addressId != null) {
                        viewModel.loadFormForEdit(addressId)
                    } else {
                        viewModel.resetForm()
                    }
                }

                AddEditAddressScreen(
                    viewModel = viewModel,
                    onBackClick = { finish() },
                    onSaved = {
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                )
            }
        }
    }
}
