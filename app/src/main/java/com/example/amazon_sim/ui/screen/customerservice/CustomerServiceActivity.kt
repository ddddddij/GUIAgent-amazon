package com.example.amazon_sim.ui.screen.customerservice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amazon_sim.ui.theme.Amazon_simTheme

class CustomerServiceActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Amazon_simTheme {
                val viewModel: CustomerServiceViewModel = viewModel()
                val messages by viewModel.messages.collectAsStateWithLifecycle()
                val inputText by viewModel.inputText.collectAsStateWithLifecycle()

                CustomerServiceScreen(
                    messages = messages,
                    inputText = inputText,
                    onInputChange = { viewModel.onInputChange(it) },
                    onSendClick = { viewModel.sendMessage() },
                    onBackClick = { finish() }
                )
            }
        }
    }
}
