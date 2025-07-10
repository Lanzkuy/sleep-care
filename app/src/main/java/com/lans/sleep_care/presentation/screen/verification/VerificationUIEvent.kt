package com.lans.sleep_care.presentation.screen.verification

sealed class VerificationUIEvent {
    data class VerificationCodeChanged(val verificationCode: String) : VerificationUIEvent()
    data class SendVerificationCodeButtonClicked(val email: String) : VerificationUIEvent()
    data class ConfirmButtonClicked(val email: String) : VerificationUIEvent()
}