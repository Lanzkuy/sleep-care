package com.lans.sleep_care.domain.model

data class User(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val avatar: String = "",
    val age: Int = 0,
    val gender: String = "",
    val problems: List<String>? = null,
    val isActive: Int? = 0,
    val isOnline: Boolean? = false,
)
