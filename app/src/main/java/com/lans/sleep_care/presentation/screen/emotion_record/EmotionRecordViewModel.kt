package com.lans.sleep_care.presentation.screen.emotion_record

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.lans.sleep_care.data.DATA
import com.lans.sleep_care.domain.model.logbook.EmotionRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmotionRecordViewModel @Inject constructor(
) : ViewModel() {
    private val _state = mutableStateOf(EmotionRecordUIState())
    val state: State<EmotionRecordUIState> get() = _state

    fun saveEmotionRecord(record: EmotionRecord) {
        val newRecord = _state.value.localSavedEmotionRecord.toMutableList()
        newRecord.add(record)

        _state.value = _state.value.copy(localSavedEmotionRecord = newRecord)
        DATA.savedEmotionRecord = _state.value.localSavedEmotionRecord
    }
}
