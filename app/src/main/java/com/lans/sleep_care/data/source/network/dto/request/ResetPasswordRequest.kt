package com.lans.sleep_care.data.source.network.dto.request

import com.squareup.moshi.Json

data class ResetPasswordRequest(
    val email: String,
    val token: Int,
    val password: String,
    @field:Json(name = "password_confirmation")
    val passwordConfirmation: String
)
