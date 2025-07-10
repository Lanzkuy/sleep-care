package com.lans.sleep_care.presentation.screen.forgot_password

import com.lans.sleep_care.domain.model.validation.InputWrapper

data class ForgotPasswordUIState(
    val email: InputWrapper = InputWrapper(),
    val verificationCode: InputWrapper = InputWrapper(),
    val newPassword: InputWrapper = InputWrapper(),
    val newPasswordConfirmation: InputWrapper = InputWrapper(),
    val currentPage: Int = 0,
    val isCountdown: Long = System.currentTimeMillis(),
    val isForgotPasswordLoading: Boolean = false,
    val isResetPasswordLoading: Boolean = false,
    var error: String = "",
    var forgotPasswordResponse: Boolean = false,
    val resetPasswordResponse: Boolean = false
)