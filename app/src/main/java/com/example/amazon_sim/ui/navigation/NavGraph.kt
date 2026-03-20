package com.example.amazon_sim.ui.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.amazon_sim.ui.screen.address.AddressActivity
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
            val context = LocalContext.current
            HomeScreen(
                onAddressClick = {
                    context.startActivity(Intent(context, AddressActivity::class.java))
                }
            )
        }
        composable("profile") {
            val context = LocalContext.current
            ProfileScreen(
                onAccountEntryClick = { id ->
                    if (id == "addresses") {
                        context.startActivity(Intent(context, AddressActivity::class.java))
                    }
                }
            )
        }
        composable("cart") {
            CartScreen()
        }
        composable("menu") {
            MenuScreen()
        }
    }
}
