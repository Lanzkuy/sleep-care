package com.lans.sleep_care.presentation.screen.sleep_diary

import com.lans.sleep_care.domain.model.DiaryAnswer

data class SleepDiaryState(
    val localSavedAnswers: Map<Pair<String, Int>, DiaryAnswer> = mutableMapOf(),
    var isLoading: Boolean = false,
    var error: String = "",
    var registerResponse: Boolean = false
)