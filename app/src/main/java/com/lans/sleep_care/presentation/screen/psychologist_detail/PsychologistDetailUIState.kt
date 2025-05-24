package com.lans.sleep_care.presentation.screen.psychologist_detail

import com.lans.sleep_care.domain.model.Order
import com.lans.sleep_care.domain.model.Psychologist
import com.lans.sleep_care.domain.model.User

data class PsychologistDetailUIState(
    val isLoading: Boolean = false,
    val isButtonLoading: Boolean = false,
    var error: String = "",
    val psychologist: Psychologist = Psychologist(),
    val user: User = User(),
    val order: Order = Order(),
    val paymentToken: String = ""
)