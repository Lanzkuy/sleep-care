package com.lans.sleep_care.presentation.screen.emotion_record

import com.lans.sleep_care.domain.model.logbook.EmotionRecord

data class EmotionRecordUIState(
    val localSavedEmotionRecord: List<EmotionRecord> = mutableListOf(),
    var isLoading: Boolean = false,
    var error: String = ""
)