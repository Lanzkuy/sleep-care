package com.lans.sleep_care.data.source.network.dto.response.logbook

import com.squareup.moshi.Json

data class SleepDiaryListResponse(
    @field:Json(name = "sleep_diaries")
    val sleepDiaries: Map<String, List<SleepDiaryResponse>>
)
