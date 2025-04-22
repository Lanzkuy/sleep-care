package com.lans.sleep_care.presentation.screen.sleep_diary

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.lans.sleep_care.data.DATA
import com.lans.sleep_care.domain.model.DiaryAnswer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SleepDiaryViewModel @Inject constructor(
) : ViewModel() {
    private val _state = mutableStateOf(SleepDiaryState())
    val state: State<SleepDiaryState> get() = _state

    fun saveAnswers(answers: Map<Pair<String, Int>, DiaryAnswer>) {
        val newAnswers = _state.value.localSavedAnswers.toMutableMap()
        newAnswers.putAll(answers)

        _state.value = _state.value.copy(localSavedAnswers = newAnswers)
        DATA.savedAnswers = _state.value.localSavedAnswers
    }
}
