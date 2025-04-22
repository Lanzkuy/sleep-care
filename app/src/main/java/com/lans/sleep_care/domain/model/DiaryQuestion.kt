package com.lans.sleep_care.domain.model

data class DiaryQuestion(
    val id: Int,
    val text: String,
    val isYesNo: Boolean = true,
    val subQuestions: List<DiaryQuestion> = emptyList()
)
