package com.lans.sleep_care.domain.model

data class DiaryAnswer(
    val date: String,
    val questionId: Int,
    val value: String,
    val subAnswers: List<DiaryAnswer> = emptyList()
)
