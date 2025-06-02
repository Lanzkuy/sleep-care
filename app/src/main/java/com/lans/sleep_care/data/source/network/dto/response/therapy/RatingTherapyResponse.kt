package com.lans.sleep_care.data.source.network.dto.response.therapy

import com.squareup.moshi.Json

data class RatingTherapyResponse(
    @field:Json(name = "therapy_id")
    val therapyId: Int,
    val rating: Int,
    val comment: String
)
