package com.lans.sleep_care.presentation.screen.identify_value

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.lans.sleep_care.data.DATA
import com.lans.sleep_care.domain.model.ValueArea
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IdentifyValueViewModel @Inject constructor(
) : ViewModel() {
    private val _state = mutableStateOf(IdentifyValueUIState())
    val state: State<IdentifyValueUIState> get() = _state

    fun saveValueArea(answers: Map<String, ValueArea>) {
        val newAnswers = _state.value.localSavedAreaValue.toMutableMap()
        newAnswers.putAll(answers)

        _state.value = _state.value.copy(localSavedAreaValue = newAnswers)
        DATA.savedValueArea = _state.value.localSavedAreaValue
    }
}
