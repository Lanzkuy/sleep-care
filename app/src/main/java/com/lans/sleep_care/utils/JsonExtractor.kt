package com.lans.sleep_care.utils

import com.lans.sleep_care.data.Resource
import com.squareup.moshi.Moshi

fun extractErrorMessageFromJson(json: String): String? {
    val parser = Moshi.Builder().build()
    val adapter = parser.adapter(Resource.Error::class.java)
    val result = adapter.fromJson(json)

    return result?.message
}