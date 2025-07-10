package com.lans.sleep_care.presentation.screen.emotion_record

import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer

sealed class EmotionRecordUIEvent {
    data class SaveButtonClicked(
        val therapyId: Int,
        val isUpdate: Boolean,
        val recordAnswers: List<LogbookQuestionAnswer>
    ) : EmotionRecordUIEvent()
}