package com.lans.sleep_care.presentation.screen.psychologist_detail

import com.lans.sleep_care.domain.model.Psychologist

data class PsychologistDetailUIState(
    val isLoading: Boolean = false,
    var error: String = "",
    val psychologist: Psychologist = Psychologist()
)