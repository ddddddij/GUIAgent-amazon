package com.example.amazon_sim.ui.screen.customerservice

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.amazon_sim.domain.model.ChatMessage
import com.example.amazon_sim.domain.model.MessageRole
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.UUID

class CustomerServiceViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val FILE_NAME = "chat_messages.json"
    }

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        _messages.value = listOf(
            ChatMessage(
                id = UUID.randomUUID().toString(),
                role = MessageRole.ASSISTANT,
                content = "Hello! I'm Amazon's virtual assistant. How can I help you today?",
                timestamp = System.currentTimeMillis()
            )
        )
        saveToFile()
    }

    fun onInputChange(text: String) {
        _inputText.value = text
    }

    fun sendMessage() {
        val text = _inputText.value.trim()
        if (text.isEmpty() || _isLoading.value) return

        val userMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            role = MessageRole.USER,
            content = text,
            timestamp = System.currentTimeMillis()
        )
        _messages.value = _messages.value + userMessage
        _inputText.value = ""
        _isLoading.value = true
        saveToFile()

        val loadingMessage = ChatMessage(
            id = "loading",
            role = MessageRole.ASSISTANT,
            content = "",
            timestamp = System.currentTimeMillis(),
            isLoading = true
        )
        _messages.value = _messages.value + loadingMessage

        viewModelScope.launch {
            delay(1500L)
            val reply = getAutoReply(text)
            val assistantMessage = ChatMessage(
                id = UUID.randomUUID().toString(),
                role = MessageRole.ASSISTANT,
                content = reply,
                timestamp = System.currentTimeMillis()
            )
            _messages.value = _messages.value.filter { it.id != "loading" } + assistantMessage
            _isLoading.value = false
            saveToFile()
        }
    }

    private fun saveToFile() {
        val array = JSONArray()
        _messages.value.filter { !it.isLoading }.forEach { msg ->
            array.put(JSONObject().apply {
                put("id", msg.id)
                put("role", msg.role.name)
                put("content", msg.content)
                put("timestamp", msg.timestamp)
            })
        }
        File(getApplication<Application>().filesDir, FILE_NAME).writeText(array.toString(2))
    }

    private fun getAutoReply(input: String): String {
        val lower = input.lowercase()
        return when {
            lower.contains("order") && lower.contains("cancel") ->
                "To cancel an order, go to Your Orders, select the order, and click \"Cancel Items\". Note that orders already shipped cannot be cancelled."
            lower.contains("refund") ->
                "Refunds are typically processed within 3-5 business days after we receive the returned item. You can check your refund status in Your Orders."
            lower.contains("return") ->
                "You can return most items within 30 days of delivery. Go to Your Orders, select the item, and click \"Return or Replace Items\" to start the process."
            lower.contains("delivery") || lower.contains("shipping") || lower.contains("track") ->
                "You can track your package in Your Orders. Prime members enjoy free two-day shipping on eligible items. Standard delivery typically takes 3-5 business days."
            lower.contains("password") || lower.contains("account") || lower.contains("login") ->
                "To update your account settings, go to Account & Lists > Your Account. For password changes, select \"Login & Security\". Need help accessing your account? I can guide you through the recovery process."
            lower.contains("prime") ->
                "Amazon Prime offers free two-day shipping, Prime Video, Prime Music, and more for \$14.99/month or \$139/year. You can start a free 30-day trial anytime!"
            lower.contains("payment") || lower.contains("card") || lower.contains("charge") ->
                "You can manage your payment methods in Your Account > Payment Options. If you see an unexpected charge, please check your order history first. We're here to help resolve any billing concerns."
            lower.contains("hello") || lower.contains("hi") || lower.contains("hey") ->
                "Hello! Welcome to Amazon Customer Service. How can I assist you today? I can help with orders, returns, account issues, and more."
            lower.contains("thank") ->
                "You're welcome! Is there anything else I can help you with today?"
            else ->
                "Thank you for reaching out. I'd be happy to help! Could you please provide more details about your issue? You can ask about orders, returns, refunds, delivery, account settings, or Prime membership."
        }
    }
}
