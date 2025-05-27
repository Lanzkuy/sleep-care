package com.lans.sleep_care.presentation.screen.payment

data class PaymentUIState(
    val isLoading: Boolean = false,
    var error: String = "",
    val paymentStatus: String = "",
    val isUpdated: Boolean = false
)