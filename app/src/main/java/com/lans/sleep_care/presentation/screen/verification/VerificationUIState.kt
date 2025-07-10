package com.lans.sleep_care.presentation.screen.verification

import com.lans.sleep_care.domain.model.validation.InputWrapper

data class VerificationUIState(
    val otpCode: InputWrapper = InputWrapper(),
    val isCountdown: Long = System.currentTimeMillis(),
    val isRequestOtpLoading: Boolean = false,
    val isVerificationLoading: Boolean = false,
    var error: String = "",
    var requestOtpResponse: Boolean = false,
    val verificationResponse: Boolean = false
)