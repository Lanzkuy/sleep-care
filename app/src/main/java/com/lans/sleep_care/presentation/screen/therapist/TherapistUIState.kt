package com.lans.sleep_care.presentation.screen.therapist

import com.lans.instagram_clone.domain.model.InputWrapper

data class TherapistUIState(
    var search: InputWrapper = InputWrapper(),
    var isLoading: Boolean = false,
    var error: String = "",
    var therapists: String? = null
)