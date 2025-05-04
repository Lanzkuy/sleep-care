package com.lans.sleep_care.domain.model

data class Session(
    val accessToken: String,
    val tokenType: String,
    val user: User
)
