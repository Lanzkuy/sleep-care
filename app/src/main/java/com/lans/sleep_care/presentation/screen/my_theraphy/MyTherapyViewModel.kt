package com.lans.sleep_care.presentation.screen.my_theraphy

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.usecase.psychologist.GetPsychologistUseCase
import com.lans.sleep_care.domain.usecase.therapy.GetActiveTherapyUseCase
import com.lans.sleep_care.domain.usecase.therapy.GetChatHistoryUseCase
import com.lans.sleep_care.domain.usecase.therapy.GetTherapySchedulesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyTherapyViewModel @Inject constructor(
    private val getActiveTherapyUseCase: GetActiveTherapyUseCase,
    private val getPsychologistUseCase: GetPsychologistUseCase,
    private val getChatHistoryUseCase: GetChatHistoryUseCase,
    private val getTherapySchedulesUseCase: GetTherapySchedulesUseCase
) : ViewModel() {
    private val _state = mutableStateOf(MyTherapyUIState())
    val state: State<MyTherapyUIState> get() = _state

    fun loadTherapy() {
        viewModelScope.launch {
            getActiveTherapyUseCase.execute().collect { response ->
                when (response) {
                    is Resource.Success -> {
                        if (response.data != null) {
                            _state.value = _state.value.copy(
                                therapy = response.data
                            )
                            loadPsychologist(response.data.doctorId)
                            loadChatHistory(response.data.patientId)
                            loadTherapySchedules(response.data.id)
                        }
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = response.message,
                            isTherapyLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isTherapyLoading = true,
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

    private suspend fun loadChatHistory(userId: Int) {
        viewModelScope.launch {
            getChatHistoryUseCase.execute().collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            unreadMessage = response.data.filter { it.senderId != userId }
                                .count { it.readAt.isEmpty() },
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

    private fun loadTherapySchedules(therapyId: Int) {
        viewModelScope.launch {
            getTherapySchedulesUseCase.execute(therapyId).collect { response ->
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
}
