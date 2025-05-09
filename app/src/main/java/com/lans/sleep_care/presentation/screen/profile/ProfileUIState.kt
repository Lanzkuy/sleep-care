package com.lans.sleep_care.presentation.screen.profile

import com.lans.instagram_clone.domain.model.InputWrapper

data class ProfileUIState(
    val name: InputWrapper = InputWrapper(),
    val age: InputWrapper = InputWrapper(),
    val id: Int = 0,
    val gender: String = "Pria",
    val problems: MutableList<String> = mutableListOf(),
    val availableProblems: MutableList<String> = mutableListOf(),
    val isLoading: Boolean = false,
    var error: String = "",
    val isProfileUpdated: Boolean = false
)