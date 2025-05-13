package com.lans.sleep_care.presentation.screen.psychologist_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.usecase.psychologist.GetAllPsychologistUseCase
import com.lans.sleep_care.domain.usecase.psychologist.GetPsychologistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PsychologistDetailViewModel @Inject constructor(
    private val getPsychologistUseCase: GetPsychologistUseCase
) : ViewModel() {
    private val _state = mutableStateOf(PsychologistDetailUIState())
    val state: State<PsychologistDetailUIState> get() = _state

    fun onEvent(event: PsychologistDetailUIEvent) {
        if (event is PsychologistDetailUIEvent.OrderButtonClicked) {

        }
    }

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

    fun order() {
        viewModelScope.launch {

        }
    }
}
