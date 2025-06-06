package com.lans.sleep_care.domain.model.logbook

data class LogbookQuestionAnswer(
    val questionId: Int = 0,
    val answer: LogbookAnswer = LogbookAnswer(),
    val comment: String = ""
)
