package com.lans.sleep_care.domain.model.logbook

data class SleepDiary(
    val id: Int = 0,
    val therapyId: Int = 0,
    val title: String = "",
    val week: Int = 0,
    val day: Int = 0,
    val date: String = "",
    var sleepDiaryDetail: SleepDiaryDetail? = null
)
