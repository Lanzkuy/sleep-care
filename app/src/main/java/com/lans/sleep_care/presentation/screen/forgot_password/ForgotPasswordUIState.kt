package com.lans.sleep_care.presentation.screen.forgot_password

import com.lans.instagram_clone.domain.model.InputWrapper
import com.lans.sleep_care.domain.model.User

data class ForgotPasswordUIState(
    var email: InputWrapper = InputWrapper(),
    var verificationCode: InputWrapper = InputWrapper(),
    var newPassword: InputWrapper = InputWrapper(),
    var isLoading: Boolean = false,
    var error: String = "",
    var forgotPasswordResponse: Boolean = false
)