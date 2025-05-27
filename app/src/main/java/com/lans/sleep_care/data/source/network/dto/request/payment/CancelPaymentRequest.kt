package com.lans.sleep_care.data.source.network.dto.request.payment

import com.squareup.moshi.Json

data class CancelPaymentRequest(
    @field:Json(name = "order_id")
    val orderId: String
)
