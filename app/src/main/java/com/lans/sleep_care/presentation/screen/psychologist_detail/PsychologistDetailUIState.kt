package com.lans.sleep_care.presentation.screen.psychologist_detail

import com.lans.sleep_care.domain.model.therapy.Order
import com.lans.sleep_care.domain.model.therapy.Psychologist
import com.lans.sleep_care.domain.model.auth.User

data class PsychologistDetailUIState(
    val isLoading: Boolean = false,
    val isButtonLoading: Boolean = false,
    var error: String = "",
    val psychologist: Psychologist = Psychologist(),
    val user: User = User(),
    var order: Order = Order(),
    var paymentToken: String = ""
)