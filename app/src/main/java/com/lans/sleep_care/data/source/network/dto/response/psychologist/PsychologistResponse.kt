package com.lans.sleep_care.data.source.network.dto.response.psychologist

import com.lans.sleep_care.data.source.network.dto.response.user.UserResponse
import com.lans.sleep_care.domain.model.therapy.Psychologist
import com.lans.sleep_care.domain.model.auth.User
import com.squareup.moshi.Json

data class PsychologistResponse(
    val id: Int,
    @field:Json(name = "user_id")
    val userId: Int,
    @field:Json(name = "registered_year")
    val registeredYear: Int,
    val graduate: String?,
    val about: String?,
    val phone: String,
    @field:Json(name = "total_rating")
    val totalRating: Int,
    @field:Json(name = "avg_rating")
    val avgRating: Int,
    val user: UserResponse
)

fun PsychologistResponse.toDomain() = Psychologist(
    id = id,
    userId = userId,
    registeredYear = registeredYear,
    graduate = graduate ?: "",
    about = about ?: "",
    phone = phone,
    totalRating = totalRating,
    avgRating = avgRating,
    user = User(
        id = user.id,
        name = user.name,
        email = user.email,
        avatar = user.avatar ?: "",
        age = user.age,
        gender = user.gender,
        problems = user.problems ?: emptyList(),
        isActive = user.isActive ?: false,
        isOnline = user.isOnline ?: false,
        isTherapyInProgress = user.isTherapyInProgress ?: false
    )
)