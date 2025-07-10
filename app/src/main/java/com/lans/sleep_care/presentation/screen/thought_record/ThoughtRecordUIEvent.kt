package com.lans.sleep_care.presentation.screen.thought_record

import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer

sealed class ThoughtRecordUIEvent {
    data class SaveButtonClicked(
        val therapyId: Int,
        val isUpdate: Boolean,
        val recordAnswers: List<LogbookQuestionAnswer>
    ) : ThoughtRecordUIEvent()
}