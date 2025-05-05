package com.lans.sleep_care.presentation.screen.verification

import com.lans.instagram_clone.domain.model.InputWrapper
import com.lans.sleep_care.domain.model.User

data class VerificationUIState(
    var verificationCode: InputWrapper = InputWrapper(),
    var isLoading: Boolean = false,
    var error: String = "",
    var verificationResponse: Boolean = false
)