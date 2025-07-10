package com.lans.sleep_care.presentation.screen.psychologist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.therapy.Psychologist
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

    private var currentPage = 1
    private var isLastPage = false
    private var isLoadingMore = false

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
        if (isLoadingMore || isLastPage) return
        isLoadingMore = true

        viewModelScope.launch {
            getAllPsychologistUseCase.execute(currentPage).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        val newData = response.data

                        if (newData.isEmpty()) {
                            isLastPage = true
                        } else {
                            val updatedList = _state.value.psychologists + newData
                            val keyword = _state.value.search.value
                            _state.value = _state.value.copy(
                                psychologists = updatedList,
                                filteredPsychologists = filterPsychologists(updatedList, keyword)
                            )
                            currentPage++
                        }

                        _state.value = _state.value.copy(
                            isLoading = false,
                            isPaginating = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = response.message
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = currentPage == 1,
                            isPaginating = currentPage != 1
                        )
                    }
                }

                isLoadingMore = false
            }
        }
    }
}
