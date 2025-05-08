package com.lans.sleep_care.presentation.screen.register

import com.lans.instagram_clone.domain.model.InputWrapper
import com.lans.sleep_care.domain.model.User

data class RegisterUIState(
    val email: InputWrapper = InputWrapper(),
    val name: InputWrapper = InputWrapper(),
    val password: InputWrapper = InputWrapper(),
    val confirmPassword: InputWrapper = InputWrapper(),
    val age: InputWrapper = InputWrapper(),
    val gender: String = "Pria",
    var problem: String = "",
    val problems: MutableList<String> = mutableListOf(),
    val currentPage: Int = 0,
    val isLoading: Boolean = false,
    var error: String = "",
    val isRegistered: Boolean = false
)