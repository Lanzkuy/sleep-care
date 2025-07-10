package com.lans.sleep_care.data.source.network.dto.response.therapy

import com.lans.sleep_care.domain.model.therapy.TherapySchedule
import com.lans.sleep_care.utils.parseToDate
import com.squareup.moshi.Json

data class TherapyScheduleResponse(
    val id: Int,
    @field:Json(name = "therapy_id")
    val therapyId: Int,
    val title: String,
    val note: String?,
    val link: String?,
    val date: String?,
    val time: String?,
)

fun TherapyScheduleResponse.toDomain() = TherapySchedule(
    id = id,
    therapyId = therapyId,
    title = title,
    note = note ?: "",
    link = link ?: "",
    date = date?.let { parseToDate(it) } ?: "",
    time = time ?: ""
)
