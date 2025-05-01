package com.lans.sleep_care.data.source.network.dto.request

import com.squareup.moshi.Json

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    @field:Json(name = "password_confirmation")
    val passwordConfirmation: String,
    val age: Int,
    val gender: String,
    val problems: List<String>
)
