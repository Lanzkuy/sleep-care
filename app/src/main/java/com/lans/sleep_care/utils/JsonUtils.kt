package com.lans.sleep_care.utils

import com.lans.sleep_care.data.Resource
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

fun extractErrorMessageFromJson(json: String): String? {
    val parser = Moshi.Builder().build()
    val adapter = parser.adapter(Resource.Error::class.java)
    val result = adapter.fromJson(json)

    return result?.message
}

fun parseListToJson(list: List<String>): String? {
    val parser = Moshi.Builder().build()
    val type = Types.newParameterizedType(List::class.java, String::class.java)
    val adapter = parser.adapter<List<String>>(type)

    return adapter.toJson(list)
}