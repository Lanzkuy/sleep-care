package com.lans.sleep_care.presentation.screen.register

import com.lans.sleep_care.domain.model.validation.InputWrapper

data class RegisterUIState(
    val email: InputWrapper = InputWrapper(),
    val name: InputWrapper = InputWrapper(),
    val password: InputWrapper = InputWrapper(),
    val passwordConfirmation: InputWrapper = InputWrapper(),
    val age: InputWrapper = InputWrapper(),
    val gender: String = "Pria",
    val problems: List<String> = emptyList(),
    val availableProblems: MutableList<String> = mutableListOf(),
    val currentPage: Int = 0,
    val isLoading: Boolean = false,
    var error: String = "",
    val isRegistered: Boolean = false
)