package com.lans.sleep_care.presentation.screen.committed_action

import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer

sealed class CommitedActionUIEvent {
    data class SaveButtonClicked(
        val therapyId: Int,
        val isUpdate: Boolean,
        val recordAnswers: List<LogbookQuestionAnswer>
    ) : CommitedActionUIEvent()
}