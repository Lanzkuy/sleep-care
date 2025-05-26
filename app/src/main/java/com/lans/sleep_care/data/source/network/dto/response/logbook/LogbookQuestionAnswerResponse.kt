package com.lans.sleep_care.data.source.network.dto.response.logbook

import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer
import com.squareup.moshi.Json

data class LogbookQuestionAnswerResponse(
    @field:Json(name = "question_id")
    val questionId: Int,
    val answer: LogbookAnswerResponse
)

fun LogbookQuestionAnswerResponse.toDomain() = LogbookQuestionAnswer(
    questionId = questionId,
    answer = answer.toDomain()
)
