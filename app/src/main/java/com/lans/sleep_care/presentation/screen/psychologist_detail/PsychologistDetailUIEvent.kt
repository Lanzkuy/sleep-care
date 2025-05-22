package com.lans.sleep_care.presentation.screen.psychologist_detail

sealed class PsychologistDetailUIEvent {
    data object OrderButtonClicked: PsychologistDetailUIEvent()
}