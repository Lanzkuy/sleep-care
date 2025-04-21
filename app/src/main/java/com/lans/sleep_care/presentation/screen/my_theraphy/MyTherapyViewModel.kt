package com.lans.sleep_care.presentation.screen.my_theraphy

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyTherapyViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf(MyTherapyUIState())
    val state: State<MyTherapyUIState> get() = _state

    fun onEvent(event: MyTherapyUIEvent) {
        if (event is MyTherapyUIEvent.ScheduleNoteClicked) {

        }
    }
}
