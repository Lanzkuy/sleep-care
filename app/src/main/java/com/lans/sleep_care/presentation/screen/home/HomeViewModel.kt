package com.lans.sleep_care.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    fun onEvent(event: HomeUIEvent) {
        if (event is HomeUIEvent.LogoutButtonClicked) {
            logout()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase.invoke()
        }
    }
}