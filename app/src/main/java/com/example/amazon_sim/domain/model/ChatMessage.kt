package com.example.amazon_sim.domain.model

/**
 * 聊天消息模型
 * 用于支持任务：和客服聊天
 */
data class ChatMessage(
    val id: String,              // 消息ID
    val content: String,         // 消息内容
    val isFromUser: Boolean,     // 是否来自用户
    val timestamp: Long,         // 发送时间戳
    val status: MessageStatus    // 消息状态
)
