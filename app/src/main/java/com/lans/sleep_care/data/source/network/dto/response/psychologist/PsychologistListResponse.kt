package com.lans.sleep_care.data.source.network.dto.response.psychologist

import com.squareup.moshi.Json

data class PsychologistListResponse(
    @field:Json(name = "doctors")
    val psychologists: List<PsychologistResponse>
)
