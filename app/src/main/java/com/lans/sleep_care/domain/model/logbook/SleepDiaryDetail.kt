package com.lans.sleep_care.domain.model.logbook

data class SleepDiaryDetail(
    val id: Int = 0,
    val therapyId: Int = 0,
    val questions: List<LogbookQuestion> = emptyList(),
    val answers: List<LogbookQuestionAnswer> = emptyList()
)
