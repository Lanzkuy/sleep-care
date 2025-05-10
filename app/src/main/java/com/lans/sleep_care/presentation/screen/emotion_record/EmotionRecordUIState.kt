package com.lans.sleep_care.presentation.screen.emotion_record

import com.lans.sleep_care.domain.model.EmotionRecord

data class EmotionRecordUIState(
    val localSavedEmotionRecord: List<EmotionRecord> = mutableListOf(),
    var isLoading: Boolean = false,
    var error: String = ""
)