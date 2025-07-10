package com.lans.sleep_care.data.source.network.dto.response.logbook

import com.lans.sleep_care.domain.model.logbook.LogbookAnswerList
import com.squareup.moshi.Json

data class LogbookAnswerListResponse(
    val id: Int,
    @field:Json(name = "therapy_id")
    val therapyId: Int,
    val questions: List<LogbookQuestionResponse>,
    val answers: List<LogbookQuestionAnswerResponse>
)

fun LogbookAnswerListResponse.toDomain() = LogbookAnswerList(
    id = id,
    therapyId = therapyId,
    questions = questions.map { it.toDomain() },
    answers = answers.map { it.toDomain() }
)
