package com.lans.sleep_care.presentation.screen.register

sealed class RegisterUIEvent {
    data class NameChanged(val name: String): RegisterUIEvent()
    data class EmailChanged(val email: String): RegisterUIEvent()
    data class PasswordChanged(val password: String): RegisterUIEvent()
    data class PasswordConfirmationChanged(val passwordConfirmation: String): RegisterUIEvent()
    data class AgeChanged(val age: String): RegisterUIEvent()
    data class GenderSelected(val gender: String): RegisterUIEvent()
    data class ProblemChange(val problem: String): RegisterUIEvent()
    data object AddProblemButtonClicked: RegisterUIEvent()
    data object NextButtonClicked: RegisterUIEvent()
    data object PreviousButtonClicked: RegisterUIEvent()
    data object RegisterButtonClicked: RegisterUIEvent()
}