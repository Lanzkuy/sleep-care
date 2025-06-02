package com.lans.sleep_care.presentation.screen.history_detail

import com.lans.sleep_care.domain.model.therapy.Psychologist
import com.lans.sleep_care.domain.model.therapy.Therapy
import com.lans.sleep_care.domain.model.therapy.TherapySchedule

data class HistoryDetailUIState(
    var rating: Int = 0,
    var comment: String = "",
    var isLoading: Boolean = false,
    var error: String = "",
    val therapy: Therapy? = null,
    val psychologist: Psychologist = Psychologist(),
    val schedules: List<TherapySchedule> = emptyList(),
    var isRatingCreated: Boolean = false,
)