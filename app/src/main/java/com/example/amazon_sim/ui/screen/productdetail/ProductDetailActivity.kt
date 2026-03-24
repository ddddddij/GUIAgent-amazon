package com.example.amazon_sim.ui.screen.productdetail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amazon_sim.data.CartManager
import com.example.amazon_sim.data.repository.BrandRepository
import com.example.amazon_sim.data.repository.ListsRepository
import com.example.amazon_sim.ui.screen.checkout.PaymentMethodActivity
import com.example.amazon_sim.ui.screen.lists.ListsActivity
import com.example.amazon_sim.ui.screen.lists.components.CreateListBottomSheet
import com.example.amazon_sim.ui.screen.productdetail.components.AddToListBottomSheet
import com.example.amazon_sim.ui.screen.store.StoreActivity
import com.example.amazon_sim.ui.theme.Amazon_simTheme

class ProductDetailActivity : ComponentActivity() {

    companion object {
        const val EXTRA_PRODUCT_ID = "PRODUCT_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        CartManager.init(this)
        ListsRepository.init(this)

        val productId = intent.getStringExtra(EXTRA_PRODUCT_ID) ?: ""

        setContent {
            Amazon_simTheme {
                val viewModel: ProductDetailViewModel = viewModel()

                LaunchedEffect(productId) {
                    viewModel.loadProduct(productId)
                }

                val product by viewModel.product.collectAsStateWithLifecycle()
                val selectedOptions by viewModel.selectedOptions.collectAsStateWithLifecycle()
                val priceInfo by viewModel.currentPriceInfo.collectAsStateWithLifecycle()
                val quantity by viewModel.quantity.collectAsStateWithLifecycle()
                val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()
                val deliverToText by viewModel.deliverToText.collectAsStateWithLifecycle()
                val cartItems by CartManager.cartItems.collectAsStateWithLifecycle()
                val shoppingLists by ListsRepository.lists.collectAsStateWithLifecycle()

                var showAddedToCart by remember { mutableStateOf(false) }
                var showAddToList by remember { mutableStateOf(false) }
                var showCreateList by remember { mutableStateOf(false) }

                val currentProduct = product

                if (currentProduct != null) {
                    ProductDetailScreen(
                        product = currentProduct,
                        selectedOptions = selectedOptions,
                        priceInfo = priceInfo,
                        quantity = quantity,
                        isFavorite = isFavorite,
                        deliverToText = deliverToText,
                        cartItemCount = cartItems.size,
                        showAddedToCart = showAddedToCart,
                        onBackClick = { finish() },
                        onOptionSelect = { dim, optId -> viewModel.selectOption(dim, optId) },
                        onQuantityChange = { viewModel.setQuantity(it) },
                        onFavoriteClick = { viewModel.toggleFavorite() },
                        onAddToCartClick = {
                            val price = priceInfo?.price ?: return@ProductDetailScreen
                            val color = currentProduct.imagePlaceholderColors.firstOrNull() ?: 0xFFCCCCCC
                            val variantLabels = currentProduct.specGroups
                                .filter { it.options.size > 1 }
                                .mapNotNull { group ->
                                    val optId = selectedOptions[group.dimensionName]
                                    group.options.find { it.id == optId }?.label
                                }
                            val base = currentProduct.baseName.ifEmpty { currentProduct.name }
                            val cartProductName = if (variantLabels.isNotEmpty()) {
                                "$base, ${variantLabels.joinToString(", ")}"
                            } else {
                                base
                            }
                            CartManager.addToCart(
                                context = this@ProductDetailActivity,
                                productName = cartProductName,
                                price = price,
                                quantity = quantity,
                                placeholderColor = color,
                                imageAssetPath = currentProduct.imageAssetPath,
                                productId = currentProduct.id
                            )
                            showAddedToCart = true
                        },
                        onDismissBottomSheet = { showAddedToCart = false },
                        onGoToCart = {
                            showAddedToCart = false
                            finish()
                        },
                        onBuyNowClick = {
                            val price = priceInfo?.price ?: return@ProductDetailScreen
                            val variantLabel = currentProduct.specGroups.mapNotNull { group ->
                                val optId = selectedOptions[group.dimensionName]
                                group.options.find { it.id == optId }?.label
                            }.joinToString(" / ")

                            startActivity(
                                PaymentMethodActivity.createBuyNowIntent(
                                    context = this@ProductDetailActivity,
                                    productId = currentProduct.id,
                                    productName = currentProduct.name,
                                    productImage = currentProduct.imageAssetPath,
                                    variantLabel = variantLabel,
                                    unitPrice = price,
                                    quantity = quantity,
                                    freeDeliveryDate = currentProduct.freeDeliveryDate
                                )
                            )
                        },
                        onAddToListClick = {
                            showAddToList = true
                        },
                        onVisitStoreClick = {
                            val brand = BrandRepository.getByName(currentProduct.brandName)
                            if (brand != null) {
                                startActivity(
                                    Intent(this@ProductDetailActivity, StoreActivity::class.java).apply {
                                        putExtra(StoreActivity.EXTRA_BRAND_ID, brand.brandId)
                                    }
                                )
                            }
                        }
                    )
                }

                // Add to List bottom sheet
                if (showAddToList) {
                    AddToListBottomSheet(
                        lists = shoppingLists,
                        onDismiss = { showAddToList = false },
                        onDone = { selectedListIds ->
                            selectedListIds.forEach { listId ->
                                ListsRepository.addProductToList(
                                    this@ProductDetailActivity,
                                    listId,
                                    productId
                                )
                            }
                            showAddToList = false
                            val count = selectedListIds.size
                            Toast.makeText(
                                this@ProductDetailActivity,
                                "Added to $count list(s)",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onCreateList = {
                            showAddToList = false
                            showCreateList = true
                        },
                        onViewLists = {
                            showAddToList = false
                            startActivity(
                                Intent(this@ProductDetailActivity, ListsActivity::class.java)
                            )
                        }
                    )
                }

                // Create List bottom sheet (reuse existing)
                if (showCreateList) {
                    CreateListBottomSheet(
                        defaultName = ListsRepository.suggestNewListName(),
                        onDismiss = { showCreateList = false },
                        onCreate = { name ->
                            ListsRepository.createList(this@ProductDetailActivity, name)
                            showCreateList = false
                            showAddToList = true
                        },
                        onEmptyNameError = {
                            Toast.makeText(
                                this@ProductDetailActivity,
                                "List name cannot be empty",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }
}
