package com.example.amazon_sim.domain.model

/**
 * 消息状态枚举
 * 用于聊天消息的状态追踪
 */
enum class MessageStatus {
    SENDING,      // 发送中
    SENT,         // 已发送
    DELIVERED,    // 已送达
    READ,         // 已读
    FAILED        // 发送失败
}
