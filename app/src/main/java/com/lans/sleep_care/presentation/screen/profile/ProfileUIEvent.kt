package com.lans.sleep_care.presentation.screen.profile

sealed class ProfileUIEvent {
    data class NameChanged(val name: String): ProfileUIEvent()
    data class AgeChanged(val age: String): ProfileUIEvent()
    data class GenderSelected(val gender: String): ProfileUIEvent()
    data class ToggleProblem(val problem: String): ProfileUIEvent()
    data object SaveButtonClicked: ProfileUIEvent()
}