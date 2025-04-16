package com.lans.sleep_care.presentation.screen.home

sealed class HomeUIEvent {
    data object LogoutButtonClicked: HomeUIEvent()
}