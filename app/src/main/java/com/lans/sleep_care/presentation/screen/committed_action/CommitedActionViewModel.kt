package com.lans.sleep_care.presentation.screen.committed_action

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.lans.sleep_care.data.DATA
import com.lans.sleep_care.domain.model.CommittedAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommitedActionViewModel @Inject constructor(
) : ViewModel() {
    private val _state = mutableStateOf(CommitedActionState())
    val state: State<CommitedActionState> get() = _state

    fun saveCommitedAction(answers: CommittedAction) {
        val newRecord = _state.value.localSavedCommitedAction.toMutableList()
        newRecord.add(answers)

        _state.value = _state.value.copy(localSavedCommitedAction = newRecord)
        DATA.savedCommitedAction = _state.value.localSavedCommitedAction
    }
}
