package com.lans.sleep_care.data.source.network.dto.request.user

data class ProfileUpdateRequest(
    val id: Int,
    val name: String,
    val age: Int,
    val gender: String,
    val problems: List<String>
)
