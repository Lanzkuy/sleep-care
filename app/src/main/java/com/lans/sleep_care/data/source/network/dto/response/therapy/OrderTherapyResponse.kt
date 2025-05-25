package com.lans.sleep_care.data.source.network.dto.response.therapy

import com.lans.sleep_care.domain.model.therapy.Order
import com.squareup.moshi.Json

data class OrderTherapyResponse(
    val id: String,
    val status: String,
    @field:Json(name = "total_price")
    val totalPrice: Int,
    @field:Json(name = "payment_id")
    val paymentId: String?,
    @field:Json(name = "payment_status")
    val paymentStatus: String,
    @field:Json(name = "payment_type")
    val paymentType: String?,
    val therapy: TherapyResponse
)

fun OrderTherapyResponse.toDomain() = Order(
    id = id,
    status = status,
    totalPrice = totalPrice,
    paymentId = paymentId ?: "",
    paymentStatus = paymentStatus,
    paymentType = paymentType ?: "",
    therapy = therapy.toDomain()
)