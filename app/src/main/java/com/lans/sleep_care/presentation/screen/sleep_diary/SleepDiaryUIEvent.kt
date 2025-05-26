package com.lans.sleep_care.presentation.screen.sleep_diary

import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer

sealed class SleepDiaryUIEvent {
    data class SaveButtonClicked(
        val therapyId: Int,
        val questionAnswers: List<LogbookQuestionAnswer>
    ) : SleepDiaryUIEvent()
}