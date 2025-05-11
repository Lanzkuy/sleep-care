package com.lans.sleep_care.domain.model

data class Psychologist(
    val id: Int = 0,
    val userId: Int = 0,
    val registeredYear: Int = 0,
    val phone: String = "",
    val nameTitle: String = "",
    val user: User = User()
)
