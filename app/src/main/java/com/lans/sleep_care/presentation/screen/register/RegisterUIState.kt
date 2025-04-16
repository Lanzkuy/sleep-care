package com.lans.sleep_care.presentation.screen.register

import com.lans.instagram_clone.domain.model.InputWrapper
import com.lans.sleep_care.domain.model.User

data class RegisterUIState(
    var email: InputWrapper = InputWrapper(),
    var name: InputWrapper = InputWrapper(),
    var password: InputWrapper = InputWrapper(),
    var confirmPassword: InputWrapper = InputWrapper(),
    var isLoading: Boolean = false,
    var error: String = "",
    var registerResponse: Boolean = false
)