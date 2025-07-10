package com.lans.sleep_care.utils

import org.json.JSONArray
import java.text.NumberFormat
import java.util.Locale

fun String.capitalize(): String {
    return replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}

fun String.toList(): List<String> {
    return try {
        val jsonArray = JSONArray(this)
        List(jsonArray.length()) { i -> jsonArray.getString(i) }
    } catch (e: Exception) {
        listOf(this)
    }
}

fun splitName(fullName: String): Pair<String, String> {
    val parts = fullName.trim().split("\\s+".toRegex())
    val firstName = parts.firstOrNull() ?: ""
    val lastName = if (parts.size > 1) parts.subList(1, parts.size).joinToString(" ") else ""
    return Pair(firstName, lastName)
}

fun formatToRupiah(amount: Int): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    val formatted = formatter.format(amount)
    return formatted.replace("Rp", "Rp.").replace(",00", "")
}