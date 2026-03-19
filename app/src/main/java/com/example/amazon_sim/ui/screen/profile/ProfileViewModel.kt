package com.example.amazon_sim.ui.screen.profile

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ProfileHeaderState(
    val userName: String,
    val languageLabel: String,
    val avatarColor: Color
)

data class ProfileQuickAction(
    val id: String,
    val label: String
)

data class ProfileSectionState(
    val title: String,
    val subtitle: String? = null
)

data class ProfileActionSectionState(
    val title: String,
    val subtitle: String,
    val buttonText: String
)

data class ProfileBannerItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val buttonText: String,
    val backgroundColor: Color
)

data class ProfileProductItem(
    val id: String,
    val name: String,
    val priceLabel: String,
    val detailLabel: String,
    val placeholderColor: Color
)

data class ProfileHighlightItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val badgeText: String? = null,
    val backgroundColor: Color
)

data class ProfileAccountEntry(
    val id: String,
    val title: String
)

data class ProfileGiftCardState(
    val title: String,
    val balanceLabel: String,
    val primaryButtonText: String,
    val secondaryButtonText: String
)

data class ProfileNeedHelpState(
    val text: String
)

data class ProfileUiState(
    val headerState: ProfileHeaderState,
    val quickActions: List<ProfileQuickAction>,
    val ordersSection: ProfileSectionState,
    val orderBanners: List<ProfileBannerItem>,
    val buyAgainSection: ProfileActionSectionState,
    val interestsSection: ProfileActionSectionState,
    val keepShoppingSection: ProfileSectionState,
    val keepShoppingItems: List<ProfileProductItem>,
    val listsSection: ProfileActionSectionState,
    val highlightsSection: ProfileSectionState,
    val highlightItems: List<ProfileHighlightItem>,
    val accountSection: ProfileSectionState,
    val accountEntries: List<ProfileAccountEntry>,
    val giftCardState: ProfileGiftCardState,
    val needHelpState: ProfileNeedHelpState
)

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProfileUiState(
            headerState = ProfileHeaderState(
                userName = "JiayiDai",
                languageLabel = "🇺🇸 EN",
                avatarColor = Color(0xFFD8D8D8)
            ),
            quickActions = listOf(
                ProfileQuickAction("orders", "Orders"),
                ProfileQuickAction("interests", "Interests"),
                ProfileQuickAction("account", "Account"),
                ProfileQuickAction("lists", "Lists")
            ),
            ordersSection = ProfileSectionState(
                title = "Your Orders",
                subtitle = "Looks like you have no recent orders"
            ),
            orderBanners = listOf(
                ProfileBannerItem(
                    id = "order_banner_1",
                    title = "Prime member deals",
                    subtitle = "See top offers picked for your account",
                    buttonText = "See deals",
                    backgroundColor = Color(0xFF1A73E8)
                ),
                ProfileBannerItem(
                    id = "order_banner_2",
                    title = "Spring refresh",
                    subtitle = "Browse everyday essentials and home finds",
                    buttonText = "Shop now",
                    backgroundColor = Color(0xFF3B82F6)
                )
            ),
            buyAgainSection = ProfileActionSectionState(
                title = "Buy Again",
                subtitle = "See items you recently ordered and restock quickly",
                buttonText = "Visit Buy Again"
            ),
            interestsSection = ProfileActionSectionState(
                title = "Your Interests",
                subtitle = "Follow topics and discover products matched to you",
                buttonText = "Create a new Interest"
            ),
            keepShoppingSection = ProfileSectionState(
                title = "Keep shopping for"
            ),
            keepShoppingItems = listOf(
                ProfileProductItem(
                    id = "keep_1",
                    name = "Tablet protective case",
                    priceLabel = "$24.99",
                    detailLabel = "New arrivals",
                    placeholderColor = Color(0xFFC7D2FE)
                ),
                ProfileProductItem(
                    id = "keep_2",
                    name = "Portable laptop stand",
                    priceLabel = "$19.99",
                    detailLabel = "Inspired by recent views",
                    placeholderColor = Color(0xFFBFDBFE)
                ),
                ProfileProductItem(
                    id = "keep_3",
                    name = "Bluetooth keyboard",
                    priceLabel = "$34.99",
                    detailLabel = "Popular picks",
                    placeholderColor = Color(0xFFA7F3D0)
                )
            ),
            listsSection = ProfileActionSectionState(
                title = "Lists and Registries",
                subtitle = "Create a list for shopping ideas and future plans",
                buttonText = "Create a List"
            ),
            highlightsSection = ProfileSectionState(
                title = "Your Amazon highlights"
            ),
            highlightItems = listOf(
                ProfileHighlightItem(
                    id = "highlight_1",
                    title = "Prime benefits",
                    subtitle = "Fast delivery and member-only savings",
                    badgeText = "Prime",
                    backgroundColor = Color(0xFFDCEBFF)
                ),
                ProfileHighlightItem(
                    id = "highlight_2",
                    title = "Deals to watch",
                    subtitle = "Keep an eye on limited-time offers",
                    backgroundColor = Color(0xFFFFE4CC)
                ),
                ProfileHighlightItem(
                    id = "highlight_3",
                    title = "New for you",
                    subtitle = "Fresh recommendations based on your activity",
                    backgroundColor = Color(0xFFE3F7E8)
                )
            ),
            accountSection = ProfileSectionState(
                title = "Your Account"
            ),
            accountEntries = listOf(
                ProfileAccountEntry("payments", "Your Payments"),
                ProfileAccountEntry("orders_entry", "Your orders"),
                ProfileAccountEntry("memberships", "Memberships & subscriptions")
            ),
            giftCardState = ProfileGiftCardState(
                title = "Gift Card Balance",
                balanceLabel = "$0.00",
                primaryButtonText = "Redeem Gift Card",
                secondaryButtonText = "Reload Balance"
            ),
            needHelpState = ProfileNeedHelpState(
                text = "Need help? Contact Customer Service"
            )
        )
    )
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
}
