package com.example.amazon_sim.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.amazon_sim.ui.screen.profile.components.ProfileActionRows
import com.example.amazon_sim.ui.screen.profile.components.ProfileHorizontalAccountEntries
import com.example.amazon_sim.ui.screen.profile.components.ProfileHorizontalBanners
import com.example.amazon_sim.ui.screen.profile.components.ProfileHorizontalHighlights
import com.example.amazon_sim.ui.screen.profile.components.ProfileHorizontalProducts
import com.example.amazon_sim.ui.screen.profile.components.ProfileNeedHelpRow
import com.example.amazon_sim.ui.screen.profile.components.ProfileQuickActionRow
import com.example.amazon_sim.ui.screen.profile.components.ProfileSectionBlock
import com.example.amazon_sim.ui.screen.profile.components.ProfileTopBar
import com.example.amazon_sim.ui.theme.AmazonBackground

@Composable
fun ProfileTab(
    uiState: ProfileUiState,
    onSearchClick: () -> Unit = {},
    onQuickActionClick: (String) -> Unit = {},
    onSectionHeaderClick: (String) -> Unit = {},
    onActionButtonClick: (String) -> Unit = {},
    onBannerClick: (String) -> Unit = {},
    onProductClick: (String) -> Unit = {},
    onHighlightClick: (String) -> Unit = {},
    onAccountEntryClick: (String) -> Unit = {},
    onNeedHelpClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AmazonBackground)
    ) {
        ProfileTopBar(
            headerState = uiState.headerState,
            onSearchClick = onSearchClick
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                ProfileQuickActionRow(
                    actions = uiState.quickActions,
                    onActionClick = onQuickActionClick
                )
            }
            item {
                ProfileSectionBlock(
                    title = uiState.ordersSection.title,
                    subtitle = uiState.ordersSection.subtitle,
                    onHeaderClick = { onSectionHeaderClick(uiState.ordersSection.title) }
                ) {
                    ProfileHorizontalBanners(
                        banners = uiState.orderBanners,
                        onBannerClick = onBannerClick,
                        onButtonClick = onActionButtonClick
                    )
                }
            }
            item {
                ProfileSectionBlock(
                    title = uiState.buyAgainSection.title,
                    subtitle = uiState.buyAgainSection.subtitle,
                    onHeaderClick = { onSectionHeaderClick(uiState.buyAgainSection.title) }
                ) {
                    ProfileActionRows.SingleActionButton(
                        text = uiState.buyAgainSection.buttonText,
                        onClick = { onActionButtonClick(uiState.buyAgainSection.buttonText) }
                    )
                }
            }
            item {
                ProfileSectionBlock(
                    title = uiState.interestsSection.title,
                    subtitle = uiState.interestsSection.subtitle,
                    onHeaderClick = { onSectionHeaderClick(uiState.interestsSection.title) }
                ) {
                    ProfileActionRows.SingleActionButton(
                        text = uiState.interestsSection.buttonText,
                        onClick = { onActionButtonClick(uiState.interestsSection.buttonText) }
                    )
                }
            }
            item {
                ProfileSectionBlock(
                    title = uiState.keepShoppingSection.title,
                    subtitle = uiState.keepShoppingSection.subtitle,
                    onHeaderClick = { onSectionHeaderClick(uiState.keepShoppingSection.title) }
                ) {
                    ProfileHorizontalProducts(
                        products = uiState.keepShoppingItems,
                        onProductClick = onProductClick
                    )
                }
            }
            item {
                ProfileSectionBlock(
                    title = uiState.listsSection.title,
                    subtitle = uiState.listsSection.subtitle,
                    onHeaderClick = { onSectionHeaderClick(uiState.listsSection.title) }
                ) {
                    ProfileActionRows.SingleActionButton(
                        text = uiState.listsSection.buttonText,
                        onClick = { onActionButtonClick(uiState.listsSection.buttonText) }
                    )
                }
            }
            item {
                ProfileSectionBlock(
                    title = uiState.highlightsSection.title,
                    subtitle = uiState.highlightsSection.subtitle,
                    onHeaderClick = { onSectionHeaderClick(uiState.highlightsSection.title) }
                ) {
                    ProfileHorizontalHighlights(
                        highlights = uiState.highlightItems,
                        onHighlightClick = onHighlightClick
                    )
                }
            }
            item {
                ProfileSectionBlock(
                    title = uiState.accountSection.title,
                    subtitle = uiState.accountSection.subtitle,
                    onHeaderClick = { onSectionHeaderClick(uiState.accountSection.title) }
                ) {
                    ProfileHorizontalAccountEntries(
                        entries = uiState.accountEntries,
                        onEntryClick = onAccountEntryClick
                    )
                }
            }
            item {
                ProfileSectionBlock(
                    title = "${uiState.giftCardState.title}: ${uiState.giftCardState.balanceLabel}",
                    onHeaderClick = { onSectionHeaderClick(uiState.giftCardState.title) }
                ) {
                    ProfileActionRows.TwoActionButtons(
                        primaryText = uiState.giftCardState.primaryButtonText,
                        secondaryText = uiState.giftCardState.secondaryButtonText,
                        onPrimaryClick = { onActionButtonClick(uiState.giftCardState.primaryButtonText) },
                        onSecondaryClick = { onActionButtonClick(uiState.giftCardState.secondaryButtonText) }
                    )
                }
            }
            item {
                ProfileNeedHelpRow(
                    text = uiState.needHelpState.text,
                    onClick = onNeedHelpClick
                )
            }
        }
    }
}
