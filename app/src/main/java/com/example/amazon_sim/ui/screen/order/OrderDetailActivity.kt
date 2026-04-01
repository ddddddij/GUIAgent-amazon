package com.example.amazon_sim.ui.screen.order

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amazon_sim.ui.screen.checkout.PaymentMethodActivity
import com.example.amazon_sim.ui.theme.Amazon_simTheme
import org.json.JSONArray
import org.json.JSONObject

class OrderDetailActivity : ComponentActivity() {

    companion object {
        const val EXTRA_ORDER_ID = "ORDER_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val orderId = intent.getStringExtra(EXTRA_ORDER_ID) ?: ""

        setContent {
            Amazon_simTheme {
                val viewModel: OrderDetailViewModel = viewModel()

                LaunchedEffect(orderId) {
                    viewModel.loadOrder(orderId)
                }

                val order by viewModel.currentOrder.collectAsStateWithLifecycle()

                val currentOrder = order
                if (currentOrder != null) {
                    OrderDetailScreen(
                        order = currentOrder,
                        onBackClick = { finish() },
                        onCancelOrder = { viewModel.cancelOrder() },
                        onConfirmReceipt = { viewModel.confirmReceipt() },
                        onPayNow = {
                            val itemsArray = JSONArray()
                            currentOrder.items.forEach { item ->
                                val obj = JSONObject().apply {
                                    put("productId", item.productId)
                                    put("productName", item.productName)
                                    put("productImage", item.productImage)
                                    put("price", item.price)
                                    put("quantity", item.quantity)
                                    val specsArray = JSONArray()
                                    item.selectedSpec.forEach { spec ->
                                        specsArray.put(JSONObject().apply {
                                            put("specType", spec.specType)
                                            put("specValue", spec.specValue)
                                        })
                                    }
                                    put("selectedSpec", specsArray)
                                }
                                itemsArray.put(obj)
                            }
                            startActivity(
                                PaymentMethodActivity.createPayNowIntent(
                                    context = this@OrderDetailActivity,
                                    orderId = currentOrder.orderId,
                                    orderItemsJson = itemsArray.toString(),
                                    orderTotal = currentOrder.orderTotal
                                )
                            )
                        },
                        onBuyAgainClick = { item ->
                            val variantLabel = item.selectedSpec.joinToString(" / ") { "${it.specType}: ${it.specValue}" }
                            startActivity(
                                PaymentMethodActivity.createBuyNowIntent(
                                    context = this@OrderDetailActivity,
                                    productId = item.productId,
                                    productName = item.productName,
                                    productImage = item.productImage,
                                    variantLabel = variantLabel,
                                    unitPrice = item.price,
                                    quantity = item.quantity,
                                    freeDeliveryDate = ""
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}
