package com.lans.sleep_care.data.source.network.dto.request.auth

data class OtpVerifyRequest(
    val otp: String,
    val email: String
)
