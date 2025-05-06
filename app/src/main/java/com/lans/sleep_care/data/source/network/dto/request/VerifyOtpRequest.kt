package com.lans.sleep_care.data.source.network.dto.request

data class VerifyOtpRequest(
    val otp: String,
    val email: String
)
