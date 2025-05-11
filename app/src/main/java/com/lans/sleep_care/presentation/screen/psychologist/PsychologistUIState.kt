package com.lans.sleep_care.presentation.screen.psychologist

import com.lans.instagram_clone.domain.model.InputWrapper
import com.lans.sleep_care.domain.model.Psychologist

data class PsychologistUIState(
    var search: InputWrapper = InputWrapper(),
    var isLoading: Boolean = false,
    var error: String = "",
    var psychologists: List<Psychologist> = emptyList(),
    var filteredPsychologists: List<Psychologist> = emptyList()
)