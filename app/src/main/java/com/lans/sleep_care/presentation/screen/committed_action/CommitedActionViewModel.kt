package com.lans.sleep_care.presentation.screen.committed_action

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
import com.lans.sleep_care.presentation.screen.thought_record.ThoughtRecordUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommitedActionViewModel @Inject constructor(
    private val getAreasUseCase: GetAreasUseCase,
    private val getLogbookQuestionsUseCase: GetLogbookQuestionsUseCase,
    private val getLogbookAnswersUseCase: GetLogbookAnswersUseCase,
    private val createLogbookAnswerUseCase: CreateLogbookAnswerUseCase,
    private val updateLogbookAnswerUseCase: UpdateLogbookAnswerUseCase
) : ViewModel() {
    private val _state = mutableStateOf(CommitedActionUIState())
    val state: State<CommitedActionUIState> get() = _state

    fun onEvent(event: ThoughtRecordUIEvent) {
        if (event is ThoughtRecordUIEvent.SaveButtonClicked) {
            if (event.isUpdate) {
                updateLogbookAnswer(
                    therapyId = event.therapyId,
                    recordAnswers = event.recordAnswers
                )
            } else {
                createLogbookAnswer(
                    therapyId = event.therapyId,
                    recordAnswers = event.recordAnswers
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
                getLogbookQuestionsUseCase.execute("committed_action").collect { response ->
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
                recordType = "committed_action",
                therapyId = therapyId,
                week = 0
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            recordId = response.data.id,
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
                recordType = "committed_action",
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
                recordType = "committed_action",
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
