package com.lans.sleep_care.utils

import java.util.Locale

fun String.capitalize(): String {
    return replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}

fun splitName(fullName: String): Pair<String, String> {
    val parts = fullName.trim().split("\\s+".toRegex())
    val firstName = parts.firstOrNull() ?: ""
    val lastName = if (parts.size > 1) parts.subList(1, parts.size).joinToString(" ") else ""
    return Pair(firstName, lastName)
}