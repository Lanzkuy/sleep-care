package com.lans.sleep_care.domain.model.logbook

data class DiaryAnswer(
    val questionId: Int,
    val date: String,
    val value: String,
    val subAnswers: List<DiaryAnswer> = emptyList()
)
