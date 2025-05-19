package com.lans.sleep_care.presentation.screen.my_theraphy

import com.lans.sleep_care.domain.model.Psychologist
import com.lans.sleep_care.domain.model.Therapy
import com.lans.sleep_care.domain.model.TherapySchedule

data class MyTherapyUIState(
    val isTherapyLoading: Boolean = false,
    val isScheduleLoading: Boolean = false,
    var error: String = "",
    val therapy: Therapy = Therapy(),
    val schedules: List<TherapySchedule> = emptyList(),
    val psychologist: Psychologist = Psychologist()
)