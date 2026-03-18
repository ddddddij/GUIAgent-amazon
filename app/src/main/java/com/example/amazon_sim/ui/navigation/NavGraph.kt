package com.example.amazon_sim.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.amazon_sim.ui.screen.cart.CartScreen
import com.example.amazon_sim.ui.screen.home.HomeScreen
import com.example.amazon_sim.ui.screen.menu.MenuScreen
import com.example.amazon_sim.ui.screen.profile.ProfileScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen()
        }
        composable("profile") {
            ProfileScreen()
        }
        composable("cart") {
            CartScreen()
        }
        composable("menu") {
            MenuScreen()
        }
    }
}
