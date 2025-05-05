package com.lans.sleep_care.presentation.screen.login

import com.lans.instagram_clone.domain.model.InputWrapper

data class LoginUIState(
    val email: InputWrapper = InputWrapper(),
    val password: InputWrapper = InputWrapper(),
    val isLoading: Boolean = false,
    var error: String = "",
    val isLoggedIn: Boolean = false
)