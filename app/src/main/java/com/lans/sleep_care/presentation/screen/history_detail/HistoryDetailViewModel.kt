package com.lans.sleep_care.presentation.screen.history_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.usecase.psychologist.GetPsychologistUseCase
import com.lans.sleep_care.domain.usecase.therapy.GetCompletedTherapyUseCase
import com.lans.sleep_care.domain.usecase.therapy.GetTherapySchedulesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryDetailViewModel @Inject constructor(
    private val getCompletedTherapyUseCase: GetCompletedTherapyUseCase,
    private val getPsychologistUseCase: GetPsychologistUseCase,
    private val getTherapySchedulesUseCase: GetTherapySchedulesUseCase
) : ViewModel() {
    private val _state = mutableStateOf(HistoryDetailUIState())
    val state: State<HistoryDetailUIState> get() = _state

    fun loadPsychologist(id: Int) {
        viewModelScope.launch {
            getPsychologistUseCase.execute(id).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            psychologist = response.data,
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

    fun loadTherapySchedules(therapyId: Int) {
        viewModelScope.launch {
            getTherapySchedulesUseCase.execute(therapyId).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            schedules = response.data,
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        val message = response.message
                        _state.value = _state.value.copy(
                            error = message.takeIf { it != "Terapi tidak ditemukan." } ?: "",
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
