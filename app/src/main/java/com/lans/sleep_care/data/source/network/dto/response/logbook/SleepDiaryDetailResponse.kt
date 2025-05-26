package com.lans.sleep_care.data.source.network.dto.response.logbook

import com.lans.sleep_care.domain.model.logbook.SleepDiaryDetail
import com.squareup.moshi.Json

data class SleepDiaryDetailResponse(
    val id: Int,
    @field:Json(name = "therapy_id")
    val therapyId: Int,
    val questions: List<LogbookQuestionResponse>,
    val answers: List<LogbookQuestionAnswerResponse>
)

fun SleepDiaryDetailResponse.toDomain() = SleepDiaryDetail(
    id = id,
    therapyId = therapyId,
    questions = questions.map { it.toDomain() },
    answers = answers.map { it.toDomain() }
)
