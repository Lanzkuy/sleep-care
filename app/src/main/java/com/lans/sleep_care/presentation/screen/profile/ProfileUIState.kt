package com.lans.sleep_care.presentation.screen.profile

import com.lans.instagram_clone.domain.model.InputWrapper

data class ProfileUIState(
    val name: InputWrapper = InputWrapper(),
    val email: InputWrapper = InputWrapper(),
    val age: InputWrapper = InputWrapper(),
    var gender: String = "Pria",
    var problem: String = "",
    val problems: MutableList<String> = mutableListOf(),
    val isLoading: Boolean = false,
    var error: String = "",
    val isProfileUpdated: Boolean = false
)