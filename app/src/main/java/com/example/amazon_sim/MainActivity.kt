package com.example.amazon_sim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.amazon_sim.data.CartManager
import com.example.amazon_sim.data.repository.OrderRepositoryImpl
import com.example.amazon_sim.ui.components.BottomNavigationBar
import com.example.amazon_sim.ui.navigation.NavGraph
import com.example.amazon_sim.ui.theme.Amazon_simTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 每次启动 app 时重置所有数据到初始状态
        CartManager.reset(this)
        OrderRepositoryImpl.getInstance(this)  // 首次创建会自动 resetFromAssets
        OrderRepositoryImpl.resetInstance()     // 若已存在单例则强制重置

        setContent {
            Amazon_simTheme {
                val navController = rememberNavController()
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
}