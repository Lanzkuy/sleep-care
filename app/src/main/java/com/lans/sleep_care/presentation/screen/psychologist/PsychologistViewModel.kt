package com.lans.sleep_care.presentation.screen.psychologist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PsychologistViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf(PsychologistUIState())
    val state: State<PsychologistUIState> get() = _state

    fun onEvent(event: PsychologistUIEvent) {
        if (event is PsychologistUIEvent.SearchChanged) {
            _state.value = _state.value.copy(
                search = _state.value.search.copy(
                    value = event.message
                )
            )
        }
    }
}
