package com.lans.sleep_care.data.source.network.dto.request.therapy

import com.squareup.moshi.Json

data class OrderTherapyRequest(
    @field:Json(name = "doctor_id")
    val doctorId: Int
)
