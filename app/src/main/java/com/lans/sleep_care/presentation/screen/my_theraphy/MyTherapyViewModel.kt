package com.lans.sleep_care.presentation.screen.my_theraphy

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.usecase.psychologist.GetPsychologistUseCase
import com.lans.sleep_care.domain.usecase.therapy.GetTherapySchedulesUseCase
import com.lans.sleep_care.domain.usecase.therapy.GetActiveTherapyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyTherapyViewModel @Inject constructor(
    private val getActiveTherapyUseCase: GetActiveTherapyUseCase,
    private val getTherapySchedulesUseCase: GetTherapySchedulesUseCase,
    private val getPsychologistUseCase: GetPsychologistUseCase
) : ViewModel() {
    private val _state = mutableStateOf(MyTherapyUIState())
    val state: State<MyTherapyUIState> get() = _state

    fun loadTherapy() {
        viewModelScope.launch {
            getActiveTherapyUseCase.execute().collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            therapy = response.data
                        )
                        loadPsychologist(response.data.doctorId)
                    }

                    is Resource.Error -> {
                        val message = response.message
                        _state.value = _state.value.copy(
                            error = message.takeIf { it != "Terapi tidak ditemukan." } ?: "",
                            isTherapyLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isTherapyLoading = true
                        )
                    }
                }
            }
        }
    }

    fun loadTherapySchedules() {
        viewModelScope.launch {
            getTherapySchedulesUseCase.execute().collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            schedules = response.data,
                            isScheduleLoading = false
                        )
                    }

                    is Resource.Error -> {
                        val message = response.message
                        _state.value = _state.value.copy(
                            error = message.takeIf { it != "Terapi tidak ditemukan." } ?: "",
                            isScheduleLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isScheduleLoading = true
                        )
                    }
                }
            }
        }
    }


    private fun loadPsychologist(id: Int) {
        viewModelScope.launch {
            getPsychologistUseCase.execute(id).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            psychologist = response.data,
                            isTherapyLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = response.message,
                            isTherapyLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isTherapyLoading = true
                        )
                    }
                }
            }
        }
    }
}
