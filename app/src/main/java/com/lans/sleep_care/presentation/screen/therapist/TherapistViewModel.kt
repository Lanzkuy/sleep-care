package com.lans.sleep_care.presentation.screen.therapist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class TherapistViewModel : ViewModel() {
    private val _state = mutableStateOf(TherapistUIState())
    val state: State<TherapistUIState> get() = _state

    fun onEvent(event: TherapistUIEvent) {
        if (event is TherapistUIEvent.SearchChanged) {
            _state.value = _state.value.copy(
                search = _state.value.search.copy(
                    value = event.message
                )
            )
        }
    }
}
