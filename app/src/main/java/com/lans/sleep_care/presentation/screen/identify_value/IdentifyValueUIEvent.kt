package com.lans.sleep_care.presentation.screen.identify_value

import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer

sealed class IdentifyValueUIEvent {
    data class SaveButtonClicked(
        val therapyId: Int,
        val questionAnswers: List<LogbookQuestionAnswer>
    ) : IdentifyValueUIEvent()
}