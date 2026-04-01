package com.example.amazon_sim

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.amazon_sim.data.CartManager
import com.example.amazon_sim.data.repository.BrandRepository
import com.example.amazon_sim.data.repository.OrderRepositoryImpl
import com.example.amazon_sim.ui.components.BottomNavigationBar
import com.example.amazon_sim.ui.navigation.NavGraph
import com.example.amazon_sim.ui.theme.Amazon_simTheme

class MainActivity : ComponentActivity() {

    private var navigateToHomeSignal by mutableIntStateOf(0)
    private var navigateToCartSignal by mutableIntStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        CartManager.reset(this)
        BrandRepository.resetInstance()
        OrderRepositoryImpl.getInstance(this)
        OrderRepositoryImpl.resetInstance()

        if (intent?.getBooleanExtra(EXTRA_NAVIGATE_HOME, false) == true) {
            navigateToHomeSignal++
        }
        if (intent?.getBooleanExtra(EXTRA_NAVIGATE_CART, false) == true) {
            navigateToCartSignal++
        }

        setContent {
            Amazon_simTheme {
                val navController = rememberNavController()

                LaunchedEffect(navigateToHomeSignal) {
                    if (navigateToHomeSignal > 0) {
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }

                LaunchedEffect(navigateToCartSignal) {
                    if (navigateToCartSignal > 0) {
                        navController.navigate("cart") {
                            popUpTo("home") { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        startDestination = "home"
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.getBooleanExtra(EXTRA_NAVIGATE_HOME, false)) {
            navigateToHomeSignal++
        }
        if (intent.getBooleanExtra(EXTRA_NAVIGATE_CART, false)) {
            navigateToCartSignal++
        }
    }

    companion object {
        const val EXTRA_NAVIGATE_HOME = "extra_navigate_home"
        const val EXTRA_NAVIGATE_CART = "extra_navigate_cart"
    }
}
