package com.lans.sleep_care.presentation.screen.psychologist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.Psychologist
import com.lans.sleep_care.domain.usecase.psychologist.GetAllPsychologistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PsychologistViewModel @Inject constructor(
    private val getAllPsychologistUseCase: GetAllPsychologistUseCase
) : ViewModel() {
    private val _state = mutableStateOf(PsychologistUIState())
    val state: State<PsychologistUIState> get() = _state

    fun onEvent(event: PsychologistUIEvent) {
        if (event is PsychologistUIEvent.SearchChanged) {
            _state.value = _state.value.copy(
                search = _state.value.search.copy(
                    value = event.message
                ),
                filteredPsychologists = filterPsychologists(
                    psychologists = _state.value.psychologists,
                    keyword = event.message
                )
            )
        }
    }

    private fun filterPsychologists(
        psychologists: List<Psychologist>,
        keyword: String
    ): List<Psychologist> {
        return if (keyword.isBlank()) {
            psychologists
        } else {
            psychologists.filter {
                it.user.name.contains(keyword, ignoreCase = true)
            }
        }
    }

    fun loadAllPsychologist() {
        viewModelScope.launch {
            getAllPsychologistUseCase.execute().collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            psychologists = response.data,
                            filteredPsychologists = response.data,
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
