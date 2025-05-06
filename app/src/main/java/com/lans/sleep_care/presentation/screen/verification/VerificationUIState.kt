package com.lans.sleep_care.presentation.screen.verification

import com.lans.instagram_clone.domain.model.InputWrapper

data class VerificationUIState(
    val otpCode: InputWrapper = InputWrapper(),
    val isLoading: Boolean = false,
    var error: String = "",
    var requestOtpResponse: Boolean = false,
    var verificationResponse: Boolean = false
)