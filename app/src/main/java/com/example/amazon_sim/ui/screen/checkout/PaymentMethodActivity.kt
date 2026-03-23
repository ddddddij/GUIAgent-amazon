package com.example.amazon_sim.ui.screen.checkout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.amazon_sim.domain.model.CheckoutItem
import com.example.amazon_sim.domain.model.PaymentMethod
import com.example.amazon_sim.ui.theme.Amazon_simTheme
import org.json.JSONArray
import org.json.JSONObject
import androidx.compose.runtime.getValue

class PaymentMethodActivity : ComponentActivity() {

    companion object {
        const val EXTRA_SOURCE = "SOURCE"
        const val EXTRA_PRODUCT_ID = "PRODUCT_ID"
        const val EXTRA_PRODUCT_NAME = "PRODUCT_NAME"
        const val EXTRA_PRODUCT_IMAGE = "PRODUCT_IMAGE"
        const val EXTRA_VARIANT_LABEL = "VARIANT_LABEL"
        const val EXTRA_UNIT_PRICE = "UNIT_PRICE"
        const val EXTRA_QUANTITY = "QUANTITY"
        const val EXTRA_FREE_DELIVERY_DATE = "FREE_DELIVERY_DATE"
        const val EXTRA_CART_ITEMS_JSON = "CART_ITEMS_JSON"
        const val EXTRA_CART_ITEM_IDS_JSON = "CART_ITEM_IDS_JSON"

        const val SOURCE_BUY_NOW = "BUY_NOW"
        const val SOURCE_CART = "CART"

        fun createBuyNowIntent(
            context: Context,
            productId: String,
            productName: String,
            productImage: String,
            variantLabel: String,
            unitPrice: Double,
            quantity: Int,
            freeDeliveryDate: String
        ): Intent = Intent(context, PaymentMethodActivity::class.java).apply {
            putExtra(EXTRA_SOURCE, SOURCE_BUY_NOW)
            putExtra(EXTRA_PRODUCT_ID, productId)
            putExtra(EXTRA_PRODUCT_NAME, productName)
            putExtra(EXTRA_PRODUCT_IMAGE, productImage)
            putExtra(EXTRA_VARIANT_LABEL, variantLabel)
            putExtra(EXTRA_UNIT_PRICE, unitPrice)
            putExtra(EXTRA_QUANTITY, quantity)
            putExtra(EXTRA_FREE_DELIVERY_DATE, freeDeliveryDate)
        }

        fun createCartCheckoutIntent(
            context: Context,
            cartItemsJson: String,
            cartItemIdsJson: String
        ): Intent = Intent(context, PaymentMethodActivity::class.java).apply {
            putExtra(EXTRA_SOURCE, SOURCE_CART)
            putExtra(EXTRA_CART_ITEMS_JSON, cartItemsJson)
            putExtra(EXTRA_CART_ITEM_IDS_JSON, cartItemIdsJson)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel = CheckoutViewModelHolder.get(application)
        val source = intent.getStringExtra(EXTRA_SOURCE) ?: SOURCE_BUY_NOW

        // Parse items and set into ViewModel
        if (viewModel.checkoutItems.value.isEmpty()) {
            when (source) {
                SOURCE_BUY_NOW -> {
                    val item = CheckoutItem(
                        productId = intent.getStringExtra(EXTRA_PRODUCT_ID) ?: "",
                        productName = intent.getStringExtra(EXTRA_PRODUCT_NAME) ?: "",
                        productImageRes = intent.getStringExtra(EXTRA_PRODUCT_IMAGE) ?: "",
                        variantLabel = intent.getStringExtra(EXTRA_VARIANT_LABEL) ?: "",
                        unitPrice = intent.getDoubleExtra(EXTRA_UNIT_PRICE, 0.0),
                        quantity = intent.getIntExtra(EXTRA_QUANTITY, 1),
                        freeDeliveryDate = intent.getStringExtra(EXTRA_FREE_DELIVERY_DATE) ?: ""
                    )
                    viewModel.setCheckoutItems(listOf(item), fromCart = false, cartIds = emptyList())
                }
                SOURCE_CART -> {
                    val itemsJson = intent.getStringExtra(EXTRA_CART_ITEMS_JSON) ?: "[]"
                    val idsJson = intent.getStringExtra(EXTRA_CART_ITEM_IDS_JSON) ?: "[]"
                    val items = parseCartItemsJson(itemsJson)
                    val cartIds = parseStringArrayJson(idsJson)
                    viewModel.setCheckoutItems(items, fromCart = true, cartIds = cartIds)
                }
            }
        }

        setContent {
            Amazon_simTheme {
                val selectedMethod by viewModel.selectedPaymentMethod.collectAsStateWithLifecycle()

                PaymentMethodScreen(
                    selectedPaymentMethod = selectedMethod,
                    onSelectPaymentMethod = { viewModel.selectPaymentMethod(it) },
                    onContinue = {
                        if (selectedMethod == null) {
                            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.createOrUpdatePendingOrder()
                            startActivity(Intent(this, CheckoutActivity::class.java))
                        }
                    },
                    onCancel = {
                        CheckoutViewModelHolder.clear()
                        finish()
                    }
                )
            }
        }
    }

    private fun parseCartItemsJson(json: String): List<CheckoutItem> {
        return runCatching {
            val array = JSONArray(json)
            (0 until array.length()).map { i ->
                val obj = array.getJSONObject(i)
                CheckoutItem(
                    productId = obj.optString("productId", ""),
                    productName = obj.optString("productName", ""),
                    productImageRes = obj.optString("imageAssetPath", ""),
                    variantLabel = obj.optString("variantLabel", ""),
                    unitPrice = obj.optDouble("price", 0.0),
                    quantity = obj.optInt("quantity", 1),
                    freeDeliveryDate = obj.optString("freeDeliveryDate", "")
                )
            }
        }.getOrDefault(emptyList())
    }

    private fun parseStringArrayJson(json: String): List<String> {
        return runCatching {
            val array = JSONArray(json)
            (0 until array.length()).map { array.getString(it) }
        }.getOrDefault(emptyList())
    }
}
