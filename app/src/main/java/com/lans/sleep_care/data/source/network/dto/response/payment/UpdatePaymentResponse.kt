package com.lans.sleep_care.data.source.network.dto.response.payment

import com.squareup.moshi.Json

data class UpdatePaymentResponse(
    val id: String,
    @field:Json(name = "therapy_id")
    val therapyId: Int,
    @field:Json(name = "payment_id")
    val paymentId: String,
    @field:Json(name = "payment_status")
    val paymentStatus: String,
    @field:Json(name = "payment_type")
    val paymentType: String,
    @field:Json(name = "status")
    val status: String,
    @field:Json(name = "total_price")
    val totalPrice: Int
)
