package com.lans.sleep_care.domain.model.therapy

data class TherapySchedule(
    val id: Int = 0,
    val therapyId: Int = 0,
    val title: String = "",
    val note: String = "",
    val link: String = "",
    val date: String = "",
    val time: String = "",
)
