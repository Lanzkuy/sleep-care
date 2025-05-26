package com.lans.sleep_care.data.source.network.dto.request.logbook

data class SleepDiaryRequest(
    val therapyId: Int
)

fun SleepDiaryRequest.toQueryMap(): Map<String, Any> {
    return mapOf(
        "therapy_id" to therapyId,
    )
}