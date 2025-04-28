package com.lans.sleep_care.presentation.screen.thought_record

import com.lans.sleep_care.domain.model.ThoughtRecord
import com.lans.sleep_care.domain.model.ValueArea

data class ThoughtRecordState(
    val localSavedThoughtRecord: List<ThoughtRecord> = mutableListOf(),
    var isLoading: Boolean = false,
    var error: String = ""
)