package com.lans.sleep_care.presentation.screen.identify_value

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer
import com.lans.sleep_care.domain.usecase.logbook.CreateLogbookAnswerUseCase
import com.lans.sleep_care.domain.usecase.logbook.GetAreasUseCase
import com.lans.sleep_care.domain.usecase.logbook.GetLogbookAnswersUseCase
import com.lans.sleep_care.domain.usecase.logbook.GetLogbookQuestionsUseCase
import com.lans.sleep_care.domain.usecase.logbook.UpdateLogbookAnswerUseCase
import com.lans.sleep_care.presentation.screen.sleep_diary.SleepDiaryUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IdentifyValueViewModel @Inject constructor(
    private val getAreasUseCase: GetAreasUseCase,
    private val getLogbookQuestionsUseCase: GetLogbookQuestionsUseCase,
    private val getLogbookAnswersUseCase: GetLogbookAnswersUseCase,
    private val createLogbookAnswerUseCase: CreateLogbookAnswerUseCase,
    private val updateLogbookAnswerUseCase: UpdateLogbookAnswerUseCase
) : ViewModel() {
    private val _state = mutableStateOf(IdentifyValueUIState())
    val state: State<IdentifyValueUIState> get() = _state

    fun onEvent(event: SleepDiaryUIEvent) {
        if (event is SleepDiaryUIEvent.SaveButtonClicked) {

        }
    }

    fun loadQuestions(therapyId: Int) {
        viewModelScope.launch {
            getLogbookQuestionsUseCase.execute("identify_value").collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            questions = response.data
                        )
                        loadAreas()
                        loadAnswers(therapyId)
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = response.message,
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    private fun loadAreas() {
        viewModelScope.launch {
            getAreasUseCase.execute().collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            areas = response.data
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = response.message,
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    private fun loadAnswers(therapyId: Int) {
        viewModelScope.launch {
            getLogbookAnswersUseCase.execute(
                recordType = "identify_value",
                therapyId = therapyId
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            answers = response.data,
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
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }
}
