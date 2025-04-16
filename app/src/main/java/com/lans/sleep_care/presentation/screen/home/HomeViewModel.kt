package com.lans.sleep_care.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    fun onEvent(event: HomeUIEvent) {
        if (event is HomeUIEvent.LogoutButtonClicked) {
            logout()
        }
    }

    private fun logout() {
        viewModelScope.launch {

        }
    }
}