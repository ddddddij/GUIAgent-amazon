package com.example.amazon_sim.domain.model

/**
 * 收货地址模型
 * 用于支持任务：管理收货地址（新增/选择）
 */
data class Address(
    val id: String,              // 地址ID
    val recipientName: String,   // 收件人姓名
    val phoneNumber: String,     // 电话号码
    val province: String,        // 省份
    val city: String,            // 城市
    val district: String,        // 区县
    val detailAddress: String,   // 详细地址
    val isDefault: Boolean,      // 是否默认地址
    val createdAt: Long          // 创建时间戳
)
