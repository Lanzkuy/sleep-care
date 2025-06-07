package com.lans.sleep_care.data.source.network.dto.request.therapy

import com.squareup.moshi.Json

data class RatingTherapyRequest(
    @field:Json(name = "therapy_id")
    val therapyId: Int,
    val rating: Double,
    val comment: String
)
