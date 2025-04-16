package com.lans.sleep_care.presentation.screen.history

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf(HistoryUIState())
    val state: State<HistoryUIState> get() = _state

    fun onEvent(event: HistoryUIEvent) {
        if (event is HistoryUIEvent.HistoryItemClicked) {

        }
    }
}
