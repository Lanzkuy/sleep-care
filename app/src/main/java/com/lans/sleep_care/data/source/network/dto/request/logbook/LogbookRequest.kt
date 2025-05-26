package com.lans.sleep_care.data.source.network.dto.request.logbook

import com.squareup.moshi.Json

data class LogbookRequest(
    @field:Json(name = "therapy_id")
    val therapyId: Int,
    @field:Json(name = "record_type")
    val recordType: String,
    @field:Json(name = "record_id")
    val recordId: Int,
    val answers: List<LogbookAnswerRequest>
)
