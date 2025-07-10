package com.lans.sleep_care.presentation.screen.history_detail

sealed class HistoryDetailUIEvent {
    data object PostCommentButtonClicked : HistoryDetailUIEvent()
}