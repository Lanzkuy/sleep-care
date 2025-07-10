package com.lans.sleep_care.data.source.network.dto.request.logbook

import com.squareup.moshi.Json

data class LogbookAnswerUpsertRequest(
    @field:Json(name = "question_id")
    val questionId: Int,
    val id: Int? = null,
    val type: String,
    val answer: String,
    val note: String?
)
