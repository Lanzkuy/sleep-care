package com.lans.sleep_care.presentation.screen.sleep_diary

import com.lans.sleep_care.domain.model.logbook.LogbookQuestion
import com.lans.sleep_care.domain.model.logbook.SleepDiary

data class SleepDiaryUIState(
    var isLoading: Boolean = false,
    var error: String = "",
    val sleepDiaries: List<SleepDiary> = emptyList(),
    val questions: List<LogbookQuestion> = emptyList(),
    var isUpdated: Boolean = false
)