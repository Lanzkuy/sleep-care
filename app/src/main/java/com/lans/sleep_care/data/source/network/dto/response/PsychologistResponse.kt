package com.lans.sleep_care.data.source.network.dto.response

import com.lans.sleep_care.domain.model.Psychologist
import com.lans.sleep_care.domain.model.User
import com.squareup.moshi.Json

data class PsychologistResponse(
    val id: Int,
    @field:Json(name = "user_id")
    val userId: Int,
    @field:Json(name = "registered_year")
    val registeredYear: Int,
    val phone: String,
    @field:Json(name = "name_title")
    val nameTitle: String?,
    val user: UserResponse
)

fun PsychologistResponse.toDomain() = Psychologist(
    id = id,
    userId = userId,
    registeredYear = registeredYear,
    phone = phone,
    nameTitle = nameTitle ?: "",
    user = User(
        id = user.id,
        name = user.name,
        email = user.email,
        age = user.age,
        gender = user.gender,
        problems = user.problems ?: emptyList(),
        isActive = user.isActive,
        isOnline = user.isOnline
    )
)