package com.lans.sleep_care.data.source.network.dto.request.payment

import com.squareup.moshi.Json

data class UpdatePaymentRequest(
    @field:Json(name = "order_id")
    val orderId: String
)
