package com.lans.sleep_care.presentation.screen.payment

import com.lans.sleep_care.domain.model.Psychologist
import com.lans.sleep_care.domain.model.User

data class PaymentUIState(
    val isLoading: Boolean = false,
    var error: String = "",
    val psychologist: Psychologist? = null,
    val user: User? = null,
    val paymentStatus: String = ""
)