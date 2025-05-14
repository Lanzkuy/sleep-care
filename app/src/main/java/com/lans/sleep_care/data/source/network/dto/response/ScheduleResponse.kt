package com.lans.sleep_care.data.source.network.dto.response

import com.lans.sleep_care.domain.model.TherapySchedule
import com.lans.sleep_care.utils.parseToDate

data class ScheduleResponse(
    val id: Int,
    val therapyId: Int,
    val title: String,
    val note: String?,
    val link: String?,
    val date: String,
    val time: String,
)

fun ScheduleResponse.toDomain() = TherapySchedule(
    id = id,
    therapyId = therapyId,
    title = title,
    note = note?.substring(0, 10) ?: "",
    link = link ?: "",
    date = parseToDate(date),
    time = time
)
