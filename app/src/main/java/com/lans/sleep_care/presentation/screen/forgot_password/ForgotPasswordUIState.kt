package com.lans.sleep_care.presentation.screen.forgot_password

import com.lans.instagram_clone.domain.model.InputWrapper

data class ForgotPasswordUIState(
    val email: InputWrapper = InputWrapper(),
    val verificationCode: InputWrapper = InputWrapper(),
    val newPassword: InputWrapper = InputWrapper(),
    val newPasswordConfirmation: InputWrapper = InputWrapper(),
    val currentPage: Int = 0,
    val isLoading: Boolean = false,
    var error: String = "",
    var forgotPasswordResponse: Boolean = false,
    val resetPasswordResponse: Boolean = false
)