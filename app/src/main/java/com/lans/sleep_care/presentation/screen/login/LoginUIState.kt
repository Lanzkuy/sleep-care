package com.lans.sleep_care.presentation.screen.login

import com.lans.instagram_clone.domain.model.InputWrapper
import com.lans.sleep_care.domain.model.User

data class LoginUIState(
    var email: InputWrapper = InputWrapper(),
    var password: InputWrapper = InputWrapper(),
    var isLoading: Boolean = false,
    var error: String = "",
    var loginResponse: User? = null
)