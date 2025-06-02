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

    fun onEvent(event: IdentifyValueUIEvent) {
        if (event is IdentifyValueUIEvent.SaveButtonClicked) {
            val createdAnswers = event.recordAnswers.filter { it.answer.id == 0 }

            val updatedAnswers = event.recordAnswers.filter { it.answer.id != 0 }


            if (createdAnswers.isNotEmpty()) {
                createLogbookAnswer(
                    therapyId = event.therapyId,
                    recordAnswers = createdAnswers
                )
            }

            if (updatedAnswers.isNotEmpty()) {
                updateLogbookAnswer(
                    therapyId = event.therapyId,
                    recordAnswers = updatedAnswers
                )
            }
        }
    }

    fun loadQuestions(therapyId: Int, isReadOnly: Boolean) {
        viewModelScope.launch {
            if (isReadOnly) {
                loadAreas()
                loadAnswers(therapyId)
            } else {
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
                therapyId = therapyId,
                week = 0
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            recordId = response.data.id,
                            questions = response.data.questions,
                            answers = response.data.answers,
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

    private fun createLogbookAnswer(
        therapyId: Int,
        recordAnswers: List<LogbookQuestionAnswer>
    ) {
        viewModelScope.launch {
            createLogbookAnswerUseCase.execute(
                therapyId = therapyId,
                recordId = _state.value.recordId,
                recordType = "identify_value",
                questionAnswers = recordAnswers
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isCreated = response.data,
                            isLoading = false
                        )
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

    private fun updateLogbookAnswer(
        therapyId: Int,
        recordAnswers: List<LogbookQuestionAnswer>
    ) {
        viewModelScope.launch {
            updateLogbookAnswerUseCase.execute(
                therapyId = therapyId,
                recordId = _state.value.recordId,
                recordType = "identify_value",
                questionAnswers = recordAnswers
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        val updatedAnswers = _state.value.answers.map { oldAnswer ->
                            val newAnswer =
                                recordAnswers.find { it.answer.id == oldAnswer.answer.id }
                            if (newAnswer != null) {
                                oldAnswer.copy(answer = newAnswer.answer)
                            } else {
                                oldAnswer
                            }
                        }

                        _state.value = _state.value.copy(
                            answers = updatedAnswers,
                            isUpdated = response.data,
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


//fun loadCompletedTherapy(therapyId: Int) {
//    viewModelScope.launch {
//        getCompletedTherapyUseCase.execute().collect { response ->
//            when (response) {
//                is Resource.Success -> {
//                    val therapy = response.data.find { it.id == therapyId }
//                    if (therapy != null) {
//                        _state.value = _state.value.copy(
//                            therapy = therapy
//                        )
//                        loadPsychologist(therapy.doctorId)
//                        loadChatHistory(therapy.patientId)
//                        loadTherapySchedules(therapy.id)
//                    } else {
//                        _state.value = _state.value.copy(
//                            isTherapyLoading = false
//                        )
//                    }
//                }
//
//                is Resource.Error -> {
//                    _state.value = _state.value.copy(
//                        error = response.message,
//                        isTherapyLoading = false
//                    )
//                }
//
//                is Resource.Loading -> {
//                    _state.value = _state.value.copy(
//                        isTherapyLoading = true,
//                        isScheduleLoading = true
//                    )
//                }
//            }
//        }
//    }
//}