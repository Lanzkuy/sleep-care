package com.lans.sleep_care.presentation.screen.psychologist_detail

import com.lans.sleep_care.domain.model.Psychologist

data class PsychologistDetailUIState(
    var isLoading: Boolean = false,
    var error: String = "",
    var psychologist: Psychologist = Psychologist()
)