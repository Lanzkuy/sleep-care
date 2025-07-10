package com.lans.sleep_care.presentation.screen.psychologist

sealed class PsychologistUIEvent {
    data class SearchChanged(val message: String): PsychologistUIEvent()
}