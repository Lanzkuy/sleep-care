package com.lans.sleep_care.domain.model.auth

data class Session(
    val accessToken: String,
    val tokenType: String,
    val user: User
)
