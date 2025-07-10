package com.lans.sleep_care.presentation.screen.change_password

import com.lans.sleep_care.domain.model.validation.InputWrapper

data class ChangePasswordUIState(
    val currentPassword: InputWrapper = InputWrapper(),
    val newPassword: InputWrapper = InputWrapper(),
    val newPasswordConfirmation: InputWrapper = InputWrapper(),
    val isLoading: Boolean = false,
    var error: String = "",
    val isPasswordChanged: Boolean = false
)