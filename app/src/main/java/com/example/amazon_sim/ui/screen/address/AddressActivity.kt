package com.example.amazon_sim.ui.screen.address

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.amazon_sim.ui.theme.Amazon_simTheme

class AddressActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Amazon_simTheme {
                AddressScreen(
                    onBackClick = { finish() }
                )
            }
        }
    }
}
