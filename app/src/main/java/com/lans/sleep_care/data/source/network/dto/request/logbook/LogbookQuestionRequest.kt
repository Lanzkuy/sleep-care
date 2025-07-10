package com.lans.sleep_care.data.source.network.dto.request.logbook

data class LogbookQuestionRequest (
    val recordType: String
)

fun LogbookQuestionRequest.toQueryMap(): Map<String, Any> {
    return mapOf(
        "record_type" to recordType,
    )
}