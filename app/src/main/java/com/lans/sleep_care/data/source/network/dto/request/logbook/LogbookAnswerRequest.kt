package com.lans.sleep_care.data.source.network.dto.request.logbook

data class LogbookAnswerRequest(
    val recordType: String,
    val therapyId: Int,
    val week: Int
)

fun LogbookAnswerRequest.toQueryMap(): Map<String, Any> {
    val map = mutableMapOf<String, Any>(
        "record_type" to recordType,
        "therapy_id" to therapyId
    )
    if (week != 0) {
        map["week"] = week
    }
    return map
}