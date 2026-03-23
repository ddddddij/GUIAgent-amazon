package com.example.amazon_sim.ui.screen.address

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.amazon_sim.ui.theme.Amazon_simTheme

class AddressActivity : ComponentActivity() {

    companion object {
        const val EXTRA_SELECT_MODE = "SELECT_MODE"
        const val RESULT_SELECTED_ADDRESS_ID = "SELECTED_ADDRESS_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val selectMode = intent.getBooleanExtra(EXTRA_SELECT_MODE, false)

        setContent {
            Amazon_simTheme {
                AddressScreen(
                    onBackClick = { finish() },
                    selectMode = selectMode,
                    onAddressSelected = { addressId ->
                        val resultIntent = Intent().apply {
                            putExtra(RESULT_SELECTED_ADDRESS_ID, addressId)
                        }
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    }
                )
            }
        }
    }
}
