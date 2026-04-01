package com.example.amazon_sim.data.repository

import android.content.Context
import com.example.amazon_sim.domain.model.ShoppingList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.UUID

object ListsRepository {

    private const val PREFS_NAME = "shopping_lists_prefs"
    private const val KEY_LISTS = "lists_data"
    private const val FILE_NAME = "lists_data.json"
    private const val ASSET_PATH = "data/lists.json"

    private val _lists = MutableStateFlow<List<ShoppingList>>(emptyList())
    val lists: StateFlow<List<ShoppingList>> = _lists.asStateFlow()

    private var initialized = false

    fun init(context: Context) {
        if (initialized) return
        initialized = true
        val json = runCatching {
            context.applicationContext.assets.open(ASSET_PATH)
                .bufferedReader()
                .use { it.readText() }
        }.getOrNull()
        _lists.value = if (json != null) parseJson(json) else defaultLists()
        save(context)
    }

    fun getAllLists(): StateFlow<List<ShoppingList>> = lists

    fun getListById(listId: String): ShoppingList? {
        return _lists.value.find { it.listId == listId }
    }

    fun createList(context: Context, name: String): ShoppingList {
        val newList = ShoppingList(
            listId = "list_${UUID.randomUUID().toString().take(8)}",
            listName = name,
            createdAt = System.currentTimeMillis(),
            productIds = emptyList()
        )
        _lists.value = _lists.value + newList
        save(context)
        return newList
    }

    fun addProductToList(context: Context, listId: String, productId: String) {
        _lists.value = _lists.value.map { list ->
            if (list.listId == listId && productId !in list.productIds) {
                list.copy(productIds = list.productIds + productId)
            } else list
        }
        save(context)
    }

    /** 统计以 "Shopping List" 开头的清单数量，返回建议名 "Shopping List N+1" */
    fun suggestNewListName(): String {
        val count = _lists.value.count { it.listName.startsWith("Shopping List") }
        return "Shopping List ${count + 1}"
    }

    private fun defaultLists(): List<ShoppingList> {
        val now = System.currentTimeMillis()
        return listOf(
            ShoppingList("list_shopping", "Shopping List", now, listOf("prod_003", "prod_001")),
            ShoppingList("list_alexa", "Alexa List", now - 1000, emptyList())
        )
    }

    private fun save(context: Context) {
        val array = toJsonArray()
        context.applicationContext
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_LISTS, array.toString())
            .apply()
        File(context.applicationContext.filesDir, FILE_NAME).writeText(array.toString(2))
    }

    private fun toJsonArray(): JSONArray {
        val array = JSONArray()
        _lists.value.forEach { list ->
            array.put(JSONObject().apply {
                put("listId", list.listId)
                put("listName", list.listName)
                put("createdAt", list.createdAt)
                val ids = JSONArray()
                list.productIds.forEach { ids.put(it) }
                put("productIds", ids)
            })
        }
        return array
    }

    private fun parseJson(json: String): List<ShoppingList> {
        return runCatching {
            val array = JSONArray(json)
            buildList(array.length()) {
                for (i in 0 until array.length()) {
                    val obj = array.optJSONObject(i) ?: continue
                    val ids = obj.optJSONArray("productIds")
                    add(
                        ShoppingList(
                            listId = obj.optString("listId"),
                            listName = obj.optString("listName"),
                            createdAt = obj.optLong("createdAt", 0L),
                            productIds = if (ids != null) {
                                (0 until ids.length()).map { ids.optString(it) }
                            } else emptyList()
                        )
                    )
                }
            }
        }.getOrDefault(emptyList())
    }

    /** 用于测试或重启时重置 */
    fun resetForTesting() {
        initialized = false
    }
}
