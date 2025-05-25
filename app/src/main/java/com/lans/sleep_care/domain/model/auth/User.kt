package com.lans.sleep_care.domain.model.auth

data class User(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val avatar: String = "",
    val age: Int = 0,
    val gender: String = "",
    val problems: List<String> = emptyList(),
    val isActive: Boolean = false,
    val isOnline: Boolean = false,
    val isTherapyInProgress: Boolean = false
)
