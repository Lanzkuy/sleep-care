package com.lans.sleep_care.data.source.network.dto.response.logbook

import com.lans.sleep_care.domain.model.logbook.SleepDiary
import com.squareup.moshi.Json

data class SleepDiaryResponse(
    val id: Int,
    @field:Json(name = "therapy_id")
    val therapyId: Int,
    val title: String,
    val week: Int,
    val day: Int,
    val date: String
)

fun SleepDiaryResponse.toDomain() = SleepDiary(
    id = id,
    therapyId = therapyId,
    title = title,
    week = week,
    day = day,
    date = date
)