package com.lans.sleep_care.data.source.network.dto.request.user

import com.squareup.moshi.Json

data class PasswordChangeRequest(
    @field:Json(name = "current_password")
    val currentPassword: String,
    @field:Json(name = "new_password")
    val newPassword: String,
    @field:Json(name = "new_password_confirmation")
    val newPasswordConfirmation: String
)
