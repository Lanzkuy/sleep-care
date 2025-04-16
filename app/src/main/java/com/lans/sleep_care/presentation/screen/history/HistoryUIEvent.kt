package com.lans.sleep_care.presentation.screen.history

sealed class HistoryUIEvent {
    data class HistoryItemClicked(val historyId: String): HistoryUIEvent()
}