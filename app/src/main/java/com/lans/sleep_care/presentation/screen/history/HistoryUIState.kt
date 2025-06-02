package com.lans.sleep_care.presentation.screen.history

import com.lans.sleep_care.domain.model.therapy.Psychologist
import com.lans.sleep_care.domain.model.therapy.Therapy

data class HistoryUIState(
    var isLoading: Boolean = false,
    var error: String = "",
    val therapies: List<Therapy> = emptyList(),
    val psychologists: List<Psychologist> = emptyList(),
)