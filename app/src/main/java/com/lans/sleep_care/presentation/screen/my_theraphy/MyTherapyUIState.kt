package com.lans.sleep_care.presentation.screen.my_theraphy

import com.lans.sleep_care.domain.model.TherapySchedule

data class MyTherapyUIState(
    var isLoading: Boolean = false,
    var error: String = "",
    var schedules: List<TherapySchedule> = emptyList()
)