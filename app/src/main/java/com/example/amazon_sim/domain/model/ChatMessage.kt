package com.example.amazon_sim.domain.model

data class ChatMessage(
    val id: String,
    val role: MessageRole,
    val content: String,
    val timestamp: Long,
    val isLoading: Boolean = false
)
