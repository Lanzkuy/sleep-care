package com.lans.sleep_care.presentation.screen.history

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.usecase.psychologist.GetPsychologistUseCase
import com.lans.sleep_care.domain.usecase.therapy.GetCompletedTherapyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getCompletedTherapyUseCase: GetCompletedTherapyUseCase,
    private val getPsychologistUseCase: GetPsychologistUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(HistoryUIState())
    val state: State<HistoryUIState> get() = _state

    fun loadTherapies() {
        viewModelScope.launch {
            getCompletedTherapyUseCase.execute().collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            therapies = response.data
                        )

                        val jobs = response.data.map {
                            async {
                                loadAllPsychologist(it.doctorId)
                            }
                        }
                        jobs.awaitAll()

                        _state.value = _state.value.copy(isLoading = false)
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

    private suspend fun loadAllPsychologist(id: Int) {
        getPsychologistUseCase.execute(id).collect { response ->
            when (response) {
                is Resource.Success -> {
                    val updatedPsychologist = _state.value.psychologists.toMutableList().apply {
                        add(response.data)
                    }

                    _state.value = _state.value.copy(
                        psychologists = updatedPsychologist
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
