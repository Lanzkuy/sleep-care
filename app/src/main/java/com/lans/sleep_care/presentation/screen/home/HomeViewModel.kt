package com.lans.sleep_care.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
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