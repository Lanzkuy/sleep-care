package com.lans.sleep_care.presentation.screen.psychologist_detail

import com.lans.sleep_care.presentation.screen.login.LoginUIEvent

sealed class PsychologistDetailUIEvent {
    data object OrderButtonClicked: PsychologistDetailUIEvent()
}