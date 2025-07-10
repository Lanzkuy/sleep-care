package com.lans.sleep_care.data.source.network.dto.response.payment

import com.squareup.moshi.Json

data class CreatePaymentResponse(
    val token: String,
    @field:Json(name = "redirect_url")
    val redirectUrl: String
)
