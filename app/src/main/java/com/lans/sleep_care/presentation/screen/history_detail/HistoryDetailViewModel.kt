package com.lans.sleep_care.presentation.screen.history_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.usecase.psychologist.GetPsychologistUseCase
import com.lans.sleep_care.domain.usecase.therapy.CreateRatingUseCase
import com.lans.sleep_care.domain.usecase.therapy.GetCompletedTherapyUseCase
import com.lans.sleep_care.domain.usecase.therapy.GetTherapySchedulesUseCase
import com.lans.sleep_care.presentation.screen.home.HomeUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryDetailViewModel @Inject constructor(
    private val getCompletedTherapyUseCase: GetCompletedTherapyUseCase,
    private val getPsychologistUseCase: GetPsychologistUseCase,
    private val getTherapySchedulesUseCase: GetTherapySchedulesUseCase,
    private val createRatingUseCase: CreateRatingUseCase
) : ViewModel() {
    private val _state = mutableStateOf(HistoryDetailUIState())
    val state: State<HistoryDetailUIState> get() = _state

    fun onEvent(event: HistoryDetailUIEvent) {
        if (event is HistoryDetailUIEvent.PostCommentButtonClicked) {
            createRating()
        }
    }

    fun loadCompletedTherapy(therapyId: Int) {
        viewModelScope.launch {
            getCompletedTherapyUseCase.execute().collect { response ->
                when (response) {
                    is Resource.Success -> {
                        val therapy = response.data.find { it.id == therapyId }
                        if (therapy != null) {
                            _state.value = _state.value.copy(
                                therapy = therapy
                            )
                            loadPsychologist(therapy.doctorId)
                            loadTherapySchedules(therapy.id)
                        } else {
                            _state.value = _state.value.copy(
                                isLoading = false
                            )
                        }
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

    private fun loadPsychologist(id: Int) {
        viewModelScope.launch {
            getPsychologistUseCase.execute(id).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            psychologist = response.data
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

    private fun loadTherapySchedules(therapyId: Int) {
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

    private fun createRating() {
        viewModelScope.launch {
            createRatingUseCase.execute(
                therapyId = _state.value.therapy!!.id,
                rating = _state.value.rating,
                comment = _state.value.comment
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isRatingCreated = response.data
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
