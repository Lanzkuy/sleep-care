package com.lans.sleep_care.domain.model

data class Psychologist(
    val id: Int = 0,
    val userId: Int = 0,
    val registeredYear: Int = 0,
    val graduate: String = "",
    val about: String = "",
    val phone: String = "",
    val totalRating: Int = 0,
    val avgRating: Int = 0,
    val user: User = User()
)
