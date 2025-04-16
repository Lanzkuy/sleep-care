package com.lans.sleep_care.presentation.screen.history

data class HistoryUIState(
    var isLoading: Boolean = false,
    var error: String = "",
    var histories: String? = null
)