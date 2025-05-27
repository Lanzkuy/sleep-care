package com.lans.sleep_care.presentation.screen.identify_value

import com.lans.sleep_care.domain.model.logbook.LogbookAnswerList
import com.lans.sleep_care.domain.model.logbook.LogbookQuestion
import com.lans.sleep_care.domain.model.logbook.ValueArea

data class IdentifyValueUIState(
    val localSavedAreaValue: Map<String, ValueArea> = mutableMapOf(),
    var isLoading: Boolean = false,
    var error: String = "",
    val areas: List<String> = emptyList(),
    val questions: List<LogbookQuestion> = emptyList(),
    val answers: LogbookAnswerList = LogbookAnswerList()
)