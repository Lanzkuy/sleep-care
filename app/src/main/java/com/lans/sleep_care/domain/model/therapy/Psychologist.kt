package com.lans.sleep_care.domain.model.therapy

import com.lans.sleep_care.domain.model.auth.User

data class Psychologist(
    val id: Int = 0,
    val userId: Int = 0,
    val registeredYear: Int = 0,
    val graduate: String = "",
    val about: String = "",
    val phone: String = "",
    val totalRating: Int = 0,
    val avgRating: Double = 0.0,
    val user: User = User()
)
