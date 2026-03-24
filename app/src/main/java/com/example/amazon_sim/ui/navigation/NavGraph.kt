package com.example.amazon_sim.ui.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.amazon_sim.ui.screen.address.AddressActivity
import com.example.amazon_sim.ui.screen.order.OrderActivity
import com.example.amazon_sim.ui.screen.productdetail.ProductDetailActivity
import com.example.amazon_sim.ui.screen.search.SearchActivity
import com.example.amazon_sim.ui.screen.cart.CartScreen
import com.example.amazon_sim.ui.screen.checkout.PaymentMethodActivity
import com.example.amazon_sim.ui.screen.home.HomeScreen
import com.example.amazon_sim.ui.screen.lists.ListsActivity
import com.example.amazon_sim.ui.screen.customerservice.CustomerServiceActivity
import com.example.amazon_sim.ui.screen.menu.CategoryProductsActivity
import com.example.amazon_sim.ui.screen.menu.MenuScreen
import com.example.amazon_sim.ui.screen.profile.ProfileScreen
import org.json.JSONArray
import org.json.JSONObject

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
                onSearchClick = {
                    context.startActivity(Intent(context, SearchActivity::class.java))
                },
                onAddressClick = {
                    context.startActivity(Intent(context, AddressActivity::class.java))
                },
                onProductClick = { productId ->
                    context.startActivity(
                        Intent(context, ProductDetailActivity::class.java).apply {
                            putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, productId)
                        }
                    )
                }
            )
        }
        composable("profile") {
            val context = LocalContext.current
            val openOrders = {
                context.startActivity(Intent(context, OrderActivity::class.java))
            }
            val openLists = {
                context.startActivity(Intent(context, ListsActivity::class.java))
            }
            ProfileScreen(
                onSearchClick = {
                    context.startActivity(Intent(context, SearchActivity::class.java))
                },
                onQuickActionClick = { id ->
                    when (id) {
                        "orders" -> openOrders()
                        "lists" -> openLists()
                    }
                },
                onSectionHeaderClick = { title ->
                    when (title) {
                        "Your Orders" -> openOrders()
                        "Lists and Registries" -> openLists()
                    }
                },
                onAccountEntryClick = { id ->
                    when (id) {
                        "addresses" -> context.startActivity(Intent(context, AddressActivity::class.java))
                        "orders_entry" -> openOrders()
                    }
                }
            )
        }
        composable("cart") {
            val context = LocalContext.current
            CartScreen(
                onSearchClick = {
                    context.startActivity(Intent(context, SearchActivity::class.java))
                },
                onCheckout = { selectedItems ->
                    val itemsArray = JSONArray()
                    val idsArray = JSONArray()
                    selectedItems.forEach { item ->
                        itemsArray.put(JSONObject().apply {
                            put("productId", item.productId)
                            put("productName", item.productName)
                            put("imageAssetPath", item.imageAssetPath)
                            put("variantLabel", "")
                            put("price", item.price)
                            put("quantity", item.quantity)
                            put("freeDeliveryDate", "")
                        })
                        idsArray.put(item.id)
                    }
                    context.startActivity(
                        PaymentMethodActivity.createCartCheckoutIntent(
                            context = context,
                            cartItemsJson = itemsArray.toString(),
                            cartItemIdsJson = idsArray.toString()
                        )
                    )
                }
            )
        }
        composable("menu") {
            val context = LocalContext.current
            MenuScreen(
                onSearchClick = {
                    context.startActivity(Intent(context, SearchActivity::class.java))
                },
                onShortcutClick = { label ->
                    when (label) {
                        "Orders" -> context.startActivity(Intent(context, OrderActivity::class.java))
                        "Lists" -> context.startActivity(Intent(context, ListsActivity::class.java))
                    }
                },
                onCategoryClick = { category ->
                    val clickableCategories = setOf("Electronics", "Lifestyle", "Sports", "Food")
                    if (category in clickableCategories) {
                        context.startActivity(
                            Intent(context, CategoryProductsActivity::class.java).apply {
                                putExtra(CategoryProductsActivity.EXTRA_CATEGORY, category)
                            }
                        )
                    }
                },
                onActionClick = { action ->
                    when (action) {
                        "Customer Service" -> context.startActivity(
                            Intent(context, CustomerServiceActivity::class.java)
                        )
                    }
                }
            )
        }
    }
}
