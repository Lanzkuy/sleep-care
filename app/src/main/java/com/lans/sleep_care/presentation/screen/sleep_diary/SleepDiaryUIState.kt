package com.lans.sleep_care.presentation.screen.sleep_diary

import com.lans.sleep_care.domain.model.logbook.DiaryAnswer

data class SleepDiaryUIState(
    val localSavedAnswers: Map<Pair<String, Int>, DiaryAnswer> = mutableMapOf(),
    var isLoading: Boolean = false,
    var error: String = ""
)