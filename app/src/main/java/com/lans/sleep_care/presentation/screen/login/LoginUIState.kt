package com.lans.sleep_care.presentation.screen.login

import com.lans.instagram_clone.domain.model.InputWrapper

data class LoginUIState(
    var email: InputWrapper = InputWrapper(),
    var password: InputWrapper = InputWrapper(),
    var isLoading: Boolean = false,
    var error: String = "",
    var isLoggedIn: Boolean = false
)