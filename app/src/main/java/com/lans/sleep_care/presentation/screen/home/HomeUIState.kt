package com.lans.sleep_care.presentation.screen.home

import com.lans.sleep_care.domain.model.User

data class HomeUIState(
    val isLoading: Boolean = false,
    var error: String = "",
    val user: User = User()
)