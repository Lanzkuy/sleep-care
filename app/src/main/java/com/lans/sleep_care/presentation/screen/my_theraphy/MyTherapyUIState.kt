package com.lans.sleep_care.presentation.screen.my_theraphy

data class MyTherapyUIState(
    var isLoading: Boolean = false,
    var error: String = "",
    var schedules: String? = null
)