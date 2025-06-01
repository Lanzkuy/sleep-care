package com.lans.sleep_care.presentation.screen.thought_record

import com.lans.sleep_care.domain.model.logbook.LogbookQuestion
import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer

data class ThoughtRecordUIState(
    var isLoading: Boolean = false,
    var error: String = "",
    val recordId: Int = 0,
    val questions: List<LogbookQuestion> = emptyList(),
    val answers: List<LogbookQuestionAnswer> = emptyList(),
    var isCreated: Boolean = false,
    var isUpdated: Boolean = false
)