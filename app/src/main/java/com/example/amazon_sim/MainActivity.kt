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
import com.example.amazon_sim.data.repository.OrderRepositoryImpl
import com.example.amazon_sim.ui.components.BottomNavigationBar
import com.example.amazon_sim.ui.navigation.NavGraph
import com.example.amazon_sim.ui.theme.Amazon_simTheme

class MainActivity : ComponentActivity() {

    private var navigateToHomeSignal by mutableIntStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 每次启动 app 时重置所有数据到初始状态
        CartManager.reset(this)
        OrderRepositoryImpl.getInstance(this)  // 首次创建会自动 resetFromAssets
        OrderRepositoryImpl.resetInstance()     // 若已存在单例则强制重置

        if (intent?.getBooleanExtra(EXTRA_NAVIGATE_HOME, false) == true) {
            navigateToHomeSignal++
        }

        setContent {
            Amazon_simTheme {
                val navController = rememberNavController()

                LaunchedEffect(navigateToHomeSignal) {
                    if (navigateToHomeSignal > 0) {
                        navController.navigate("home") {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
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
                        modifier = Modifier.padding(innerPadding)
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
    }

    companion object {
        const val EXTRA_NAVIGATE_HOME = "extra_navigate_home"
    }
}