package com.lans.sleep_care.presentation.screen.psychologist

import com.lans.sleep_care.domain.model.validation.InputWrapper
import com.lans.sleep_care.domain.model.therapy.Psychologist

data class PsychologistUIState(
    var search: InputWrapper = InputWrapper(),
    var isLoading: Boolean = false,
    val isPaginating: Boolean = false,
    var error: String = "",
    var psychologists: List<Psychologist> = emptyList(),
    var filteredPsychologists: List<Psychologist> = emptyList()
)