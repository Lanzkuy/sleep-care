package com.lans.sleep_care.presentation.screen.sleep_diary

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer
import com.lans.sleep_care.domain.usecase.logbook.CreateLogbookAnswerUseCase
import com.lans.sleep_care.domain.usecase.logbook.GetLogbookQuestionsUseCase
import com.lans.sleep_care.domain.usecase.logbook.GetSleepDiariesUseCase
import com.lans.sleep_care.domain.usecase.logbook.GetSleepDiaryDetailUseCase
import com.lans.sleep_care.domain.usecase.logbook.UpdateLogbookAnswerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SleepDiaryViewModel @Inject constructor(
    private val getSleepDiariesUseCase: GetSleepDiariesUseCase,
    private val getSleepDiaryDetailUseCase: GetSleepDiaryDetailUseCase,
    private val getLogbookQuestionsUseCase: GetLogbookQuestionsUseCase,
    private val createLogbookAnswerUseCase: CreateLogbookAnswerUseCase,
    private val updateLogbookAnswerUseCase: UpdateLogbookAnswerUseCase
) : ViewModel() {
    private val _state = mutableStateOf(SleepDiaryUIState())
    val state: State<SleepDiaryUIState> get() = _state

    fun onEvent(event: SleepDiaryUIEvent) {
        if (event is SleepDiaryUIEvent.SaveButtonClicked) {
            updateLogbookAnswer(
                therapyId = event.therapyId,
                questionAnswers = event.questionAnswers
            )
        }
    }

    fun loadSleepDiaries(therapyId: Int, week: Int = 1) {
        if (state.value.sleepDiaries.isNotEmpty()) {
            viewModelScope.launch {
                val jobs = state.value.sleepDiaries.filter { it.week == week }.map { diary ->
                    async {
                        loadSleepDiaryDetail(diary.id, diary.therapyId)
                    }
                }
                jobs.awaitAll()
                _state.value = _state.value.copy(isLoading = false)
            }
        } else {
            viewModelScope.launch {
                getSleepDiariesUseCase.execute(therapyId).collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            val diaries = response.data
                            _state.value = _state.value.copy(
                                sleepDiaries = diaries
                            )

                            loadQuestions()

                            val jobs = diaries.filter { it.week == week }.map { diary ->
                                async {
                                    loadSleepDiaryDetail(diary.id, diary.therapyId)
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
                            _state.value = _state.value.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            getLogbookQuestionsUseCase.execute("sleep_diary").collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            questions = response.data
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

    private suspend fun loadSleepDiaryDetail(sleepDiaryId: Int, therapyId: Int) {
        getSleepDiaryDetailUseCase.execute(sleepDiaryId, therapyId).collect { response ->
            when (response) {
                is Resource.Success -> {
                    val updatedDiaries = _state.value.sleepDiaries.map { diary ->
                        if (diary.id == sleepDiaryId) {
                            diary.copy(sleepDiaryDetail = response.data)
                        } else diary
                    }
                    _state.value = _state.value.copy(
                        sleepDiaries = updatedDiaries
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(error = response.message)
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }
    }

    private fun createLogbookAnswer(therapyId: Int) {
        viewModelScope.launch {
//            createLogbookAnswerUseCase.execute(
//                therapyId = ,
//                recordId = 1,
//                recordType = "sleep_diary",
//                questionAnswer =
//            ).collect { response ->
//                when (response) {
//                    is Resource.Success -> {
//                        val diaries = response.data
//                        _state.value = _state.value.copy(
//                            sleepDiaries = diaries
//                        )
//                        val jobs = diaries.filter { it.week == week }.map { diary ->
//                            async {
//                                loadSleepDiaryDetail(diary.id, diary.therapyId)
//                            }
//                        }
//                        jobs.awaitAll()
//
//                        _state.value = _state.value.copy(isLoading = false)
//                    }
//
//                    is Resource.Error -> {
//                        _state.value = _state.value.copy(
//                            error = response.message,
//                            isLoading = false
//                        )
//                    }
//
//                    is Resource.Loading -> {
//                        _state.value = _state.value.copy(isLoading = true)
//                    }
//                }
//            }
        }
    }

    private fun updateLogbookAnswer(therapyId: Int, questionAnswers: List<LogbookQuestionAnswer>) {
        viewModelScope.launch {
            updateLogbookAnswerUseCase.execute(
                therapyId = therapyId,
                recordId = 1,
                recordType = "sleep_diary",
                questionAnswers = questionAnswers
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        val updatedSleepDiaries = _state.value.sleepDiaries.map { diary ->
                            val updatedDetail = diary.sleepDiaryDetail?.let { detail ->
                                val updatedAnswers = detail.answers.map { answer ->
                                    questionAnswers.firstOrNull { it.answer.id == answer.answer.id }
                                        ?: answer
                                }
                                detail.copy(answers = updatedAnswers)
                            }
                            diary.copy(sleepDiaryDetail = updatedDetail)
                        }

                        _state.value = _state.value.copy(
                            sleepDiaries = updatedSleepDiaries,
                            isUpdated = true,
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
