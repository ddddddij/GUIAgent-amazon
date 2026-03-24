package com.example.amazon_sim.ui.screen.customerservice

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amazon_sim.domain.model.ChatMessage
import com.example.amazon_sim.domain.model.MessageRole
import com.example.amazon_sim.ui.screen.customerservice.components.AssistantMessageBubble
import com.example.amazon_sim.ui.screen.customerservice.components.BottomInputBar
import com.example.amazon_sim.ui.screen.customerservice.components.LoadingBubble
import com.example.amazon_sim.ui.screen.customerservice.components.UserMessageBubble
import com.example.amazon_sim.ui.theme.AmazonNeedHelpGreen
import com.example.amazon_sim.ui.theme.AmazonSearchBarBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerServiceScreen(
    messages: List<ChatMessage>,
    inputText: String,
    onInputChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .border(1.dp, AmazonSearchBarBorder, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Search",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AmazonNeedHelpGreen
                )
            )
        },
        modifier = Modifier.imePadding()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                itemsIndexed(messages, key = { _, msg -> msg.id }) { index, message ->
                    when {
                        message.isLoading -> {
                            val showLabel = shouldShowAssistantLabel(messages, index)
                            LoadingBubble(showLabel = showLabel)
                        }
                        message.role == MessageRole.USER -> {
                            UserMessageBubble(content = message.content)
                        }
                        message.role == MessageRole.ASSISTANT -> {
                            val showLabel = shouldShowAssistantLabel(messages, index)
                            AssistantMessageBubble(
                                content = message.content,
                                showLabel = showLabel
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            BottomInputBar(
                inputText = inputText,
                onInputChange = onInputChange,
                onSendClick = onSendClick
            )
        }
    }
}

private fun shouldShowAssistantLabel(messages: List<ChatMessage>, index: Int): Boolean {
    if (index == 0) return true
    val prev = messages[index - 1]
    return prev.role != MessageRole.ASSISTANT || prev.isLoading
}
