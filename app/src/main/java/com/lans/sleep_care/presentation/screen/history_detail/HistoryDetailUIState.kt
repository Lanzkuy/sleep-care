package com.lans.sleep_care.presentation.screen.history_detail

import com.lans.sleep_care.domain.model.therapy.Psychologist
import com.lans.sleep_care.domain.model.therapy.TherapySchedule

data class HistoryDetailUIState(
    var isLoading: Boolean = false,
    var error: String = "",
    val psychologist: Psychologist = Psychologist(),
    val schedules: List<TherapySchedule> = emptyList()
)