package com.lans.sleep_care.data.source.network.dto.request.logbook

data class LogbookAnswerRequest(
    val recordType: String,
    val therapyId: Int
)

fun LogbookAnswerRequest.toQueryMap(): Map<String, Any> {
    return mapOf(
        "record_type" to recordType,
        "therapy_id" to therapyId
    )
}