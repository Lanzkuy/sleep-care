package com.lans.sleep_care.presentation.screen.register

sealed class RegisterUIEvent {
    data class EmailChanged(val email: String): RegisterUIEvent()
    data class NameChanged(val name: String): RegisterUIEvent()
    data class PasswordChanged(val password: String): RegisterUIEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String): RegisterUIEvent()
    data object RegisterButtonClicked: RegisterUIEvent()
}