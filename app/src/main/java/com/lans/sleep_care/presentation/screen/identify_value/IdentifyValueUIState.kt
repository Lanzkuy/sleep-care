package com.lans.sleep_care.presentation.screen.identify_value

import com.lans.sleep_care.domain.model.ValueArea

data class IdentifyValueUIState(
    val localSavedAreaValue: Map<String, ValueArea> = mutableMapOf(),
    var isLoading: Boolean = false,
    var error: String = ""
)