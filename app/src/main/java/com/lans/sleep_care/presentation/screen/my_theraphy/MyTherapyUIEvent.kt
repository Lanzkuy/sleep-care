package com.lans.sleep_care.presentation.screen.my_theraphy

sealed class MyTherapyUIEvent {
    data class ScheduleNoteClicked(val scheduleId: String): MyTherapyUIEvent()
}