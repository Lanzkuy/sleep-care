package com.lans.sleep_care.data.source.network.dto.response

import com.squareup.moshi.Json

data class UserResponse(
    val id: Int,
    val name: String,
    val email: String,
    val avatar: String?,
    val age: Int,
    val gender: String,
    val problems: List<String>?,
    @field:Json(name = "is_active")
    val isActive: Int?,
    @field:Json(name = "is_online")
    val isOnline: Boolean?,
    @field:Json(name = "created_at")
    val createdAt: String?,
    @field:Json(name = "deleted_at")
    val deletedAt: String?
)
