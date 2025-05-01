package com.lans.sleep_care.data.source.network.dto.response

import com.lans.sleep_care.domain.model.User
import com.squareup.moshi.Json

data class LoginResponse(
    @field:Json(name = "access_token")
    val accessToken: String,
    @field:Json(name = "token_type")
    val tokenType: String,
    val user: UserResponse
)

fun LoginResponse.toDomain() = User(
    id = user.id,
    name = user.name,
    email = user.email,
    age = user.age,
    gender = user.gender,
    problems = user.problems,
    isActive = user.isActive,
    isOnline = user.isOnline
)
