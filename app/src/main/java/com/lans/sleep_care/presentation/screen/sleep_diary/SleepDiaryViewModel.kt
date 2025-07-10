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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
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

    var week: Int = 1

    fun onEvent(event: SleepDiaryUIEvent) {
        if (event is SleepDiaryUIEvent.SaveButtonClicked) {
            val createdAnswers = event.recordAnswers.mapNotNull { entry ->
                val filtered = entry.value.filter { it.answer.id == 0 }
                if (filtered.isNotEmpty()) entry.key to filtered else null
            }.toMap()

            val updatedAnswers = event.recordAnswers.mapNotNull { entry ->
                val filtered = entry.value.filter { it.answer.id != 0 }
                if (filtered.isNotEmpty()) entry.key to filtered else null
            }.toMap()

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

    fun loadSleepDiaries(therapyId: Int, isReadOnly: Boolean) {
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
                                sleepDiaries = diaries,
                                comment = diaries.map { it.comment }
                            )

                            if (!isReadOnly) {
                                loadQuestions()
                            }

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
                            diary.copy(logbookAnswerList = response.data)
                        } else diary
                    }
                    _state.value = _state.value.copy(
                        questions = response.data.questions.takeIf {
                            _state.value.questions.isEmpty()
                        } ?: _state.value.questions,
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

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun createLogbookAnswer(
        therapyId: Int,
        recordAnswers: Map<Int, List<LogbookQuestionAnswer>>
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val successfulRecords = mutableListOf<Int>()
            var hasError = false
            var errorMessage: String? = null

            recordAnswers.entries.asFlow()
                .flatMapMerge { (recordId, newAnswers) ->
                    createLogbookAnswerUseCase.execute(
                        therapyId = therapyId,
                        recordId = recordId,
                        recordType = "sleep_diary",
                        questionAnswers = newAnswers
                    ).map { response -> recordId to response }
                }
                .collect { (recordId, response) ->
                    when (response) {
                        is Resource.Success -> {
                            successfulRecords.add(recordId)
                        }

                        is Resource.Error -> {
                            hasError = true
                            errorMessage = response.message
                        }

                        else -> Unit
                    }
                }

            successfulRecords.forEach { recordId ->
                loadSleepDiaryDetail(recordId, therapyId)
            }

            _state.value = _state.value.copy(
                isCreated = successfulRecords.isNotEmpty(),
                error = if (hasError) {
                    if (errorMessage?.contains("wajib diisi", ignoreCase = true) == true)
                        "Semua pertanyaan wajib diisi"
                    else
                        errorMessage.orEmpty()
                } else "",
                isLoading = false
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun updateLogbookAnswer(
        therapyId: Int,
        recordAnswers: Map<Int, List<LogbookQuestionAnswer>>
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val updatedAnswerMap = mutableMapOf<Int, List<LogbookQuestionAnswer>>()
            var hasSuccess = false
            var hasError = false
            var errorMessage: String? = null

            recordAnswers.entries.asFlow()
                .flatMapMerge { (recordId, newAnswers) ->
                    updateLogbookAnswerUseCase.execute(
                        therapyId = therapyId,
                        recordId = recordId,
                        recordType = "sleep_diary",
                        questionAnswers = newAnswers
                    ).map { response -> Triple(recordId, newAnswers, response) }
                }
                .collect { (recordId, newAnswers, response) ->
                    when (response) {
                        is Resource.Success -> {
                            hasSuccess = true
                            updatedAnswerMap[recordId] = newAnswers
                        }

                        is Resource.Error -> {
                            hasError = true
                            errorMessage = response.message
                        }

                        else -> Unit
                    }
                }

            val updatedDiaries = _state.value.sleepDiaries.map { diary ->
                val newAnswers = updatedAnswerMap[diary.id]
                val existingLogbook = diary.logbookAnswerList
                if (newAnswers != null && existingLogbook != null) {
                    val refreshedAnswers = existingLogbook.answers.map { currentAnswer ->
                        newAnswers.firstOrNull { it.answer.id == currentAnswer.answer.id }
                            ?: currentAnswer
                    }
                    diary.copy(
                        logbookAnswerList = existingLogbook.copy(
                            answers = refreshedAnswers
                        )
                    )
                } else {
                    diary
                }
            }

            _state.value = _state.value.copy(
                sleepDiaries = updatedDiaries,
                isUpdated = hasSuccess,
                error = if (hasError) {
                    if (errorMessage?.contains("wajib diisi", ignoreCase = true) == true)
                        "Semua pertanyaan wajib diisi"
                    else
                        errorMessage.orEmpty()
                } else "",
                isLoading = false
            )
        }
    }
}
