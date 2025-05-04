package com.lans.sleep_care.data.source.network.dto.response

import com.lans.sleep_care.domain.model.Session
import com.squareup.moshi.Json

data class LoginResponse(
    @field:Json(name = "access_token")
    val accessToken: String,
    @field:Json(name = "token_type")
    val tokenType: String,
    val user: UserResponse
)

fun LoginResponse.toDomain() = Session(
    accessToken = accessToken,
    tokenType = tokenType,
    user = user.toDomain()
)
