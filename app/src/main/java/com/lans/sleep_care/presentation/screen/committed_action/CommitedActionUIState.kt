package com.lans.sleep_care.presentation.screen.committed_action

import com.lans.sleep_care.domain.model.logbook.CommittedAction

data class CommitedActionUIState(
    val localSavedCommitedAction: List<CommittedAction> = mutableListOf(),
    var isLoading: Boolean = false,
    var error: String = ""
)