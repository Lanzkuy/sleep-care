package com.lans.sleep_care.presentation.screen.my_theraphy

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.usecase.therapy.GetTherapySchedulesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyTherapyViewModel @Inject constructor(
    private val getTherapySchedulesUseCase: GetTherapySchedulesUseCase
) : ViewModel() {
    private val _state = mutableStateOf(MyTherapyUIState())
    val state: State<MyTherapyUIState> get() = _state

    fun onEvent(event: MyTherapyUIEvent) {
        if (event is MyTherapyUIEvent.ScheduleNoteClicked) {

        }
    }

    fun loadTherapySchedules() {
        viewModelScope.launch {
            getTherapySchedulesUseCase.execute().collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            schedules = response.data,
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = response.message,
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }
}
