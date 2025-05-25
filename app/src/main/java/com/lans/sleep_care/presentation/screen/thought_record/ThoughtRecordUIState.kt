package com.lans.sleep_care.presentation.screen.thought_record

import com.lans.sleep_care.domain.model.logbook.ThoughtRecord

data class ThoughtRecordUIState(
    val localSavedThoughtRecord: List<ThoughtRecord> = mutableListOf(),
    var isLoading: Boolean = false,
    var error: String = ""
)