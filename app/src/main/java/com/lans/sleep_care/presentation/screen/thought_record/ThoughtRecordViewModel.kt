package com.lans.sleep_care.presentation.screen.thought_record

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.lans.sleep_care.data.DATA
import com.lans.sleep_care.domain.model.logbook.ThoughtRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThoughtRecordViewModel @Inject constructor(
) : ViewModel() {
    private val _state = mutableStateOf(ThoughtRecordUIState())
    val state: State<ThoughtRecordUIState> get() = _state

    fun saveThoughtRecord(answers: ThoughtRecord) {
        val newRecord = _state.value.localSavedThoughtRecord.toMutableList()
        newRecord.add(answers)

        _state.value = _state.value.copy(localSavedThoughtRecord = newRecord)
        DATA.savedThoughtRecord = _state.value.localSavedThoughtRecord
    }
}
