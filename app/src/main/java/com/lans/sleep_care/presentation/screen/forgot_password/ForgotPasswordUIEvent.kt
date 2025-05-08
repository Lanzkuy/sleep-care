package com.lans.sleep_care.presentation.screen.forgot_password

sealed class ForgotPasswordUIEvent {
    data class EmailChanged(val email: String) : ForgotPasswordUIEvent()
    data class VerificationCodeChanged(val verificationCode: String) : ForgotPasswordUIEvent()
    data class NewPasswordChanged(val newPassword: String) : ForgotPasswordUIEvent()
    data class NewPasswordConfirmationChanged(val newPasswordConfirmation: String) :
        ForgotPasswordUIEvent()
    data object ForgotPasswordButtonClicked : ForgotPasswordUIEvent()
    data object BackButtonClicked : ForgotPasswordUIEvent()
    data object ChangePasswordButtonClicked : ForgotPasswordUIEvent()
}