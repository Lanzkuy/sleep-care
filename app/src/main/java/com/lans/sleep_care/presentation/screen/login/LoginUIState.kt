package com.lans.sleep_care.presentation.screen.login

import com.lans.sleep_care.domain.model.validation.InputWrapper

data class LoginUIState(
    val email: InputWrapper = InputWrapper(),
    val password: InputWrapper = InputWrapper(),
    val isLoading: Boolean = false,
    var error: String = "",
    val isLoggedIn: Boolean = false
)