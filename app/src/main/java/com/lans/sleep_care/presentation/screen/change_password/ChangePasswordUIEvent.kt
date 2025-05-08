package com.lans.sleep_care.presentation.screen.change_password

sealed class ChangePasswordUIEvent {
    data class CurrentPasswordChanged(val currentPassword: String): ChangePasswordUIEvent()
    data class NewPasswordChanged(val newPassword: String): ChangePasswordUIEvent()
    data class NewPasswordConfirmationChanged(val newPasswordConfirmation: String): ChangePasswordUIEvent()
    data object UpdateButtonClicked: ChangePasswordUIEvent()
}