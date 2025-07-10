package com.lans.sleep_care.data.source.network.dto.response

data class ApiResponse<T>(
    val message: String,
    val data: T?
)
