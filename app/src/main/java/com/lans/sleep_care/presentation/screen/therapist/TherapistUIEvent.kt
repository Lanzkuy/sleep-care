package com.lans.sleep_care.presentation.screen.therapist

sealed class TherapistUIEvent {
    data class SearchChanged(val message: String): TherapistUIEvent()
}