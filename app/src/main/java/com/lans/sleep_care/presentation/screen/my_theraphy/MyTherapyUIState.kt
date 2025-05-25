package com.lans.sleep_care.presentation.screen.my_theraphy

import com.lans.sleep_care.domain.model.therapy.Psychologist
import com.lans.sleep_care.domain.model.therapy.Therapy
import com.lans.sleep_care.domain.model.therapy.TherapySchedule

data class MyTherapyUIState(
    val isTherapyLoading: Boolean = false,
    val isScheduleLoading: Boolean = false,
    var error: String = "",
    val therapy: Therapy = Therapy(),
    val psychologist: Psychologist = Psychologist(),
    val unreadMessage: Int = 0,
    val schedules: List<TherapySchedule> = emptyList()
)