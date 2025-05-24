package com.lans.sleep_care.domain.model

data class Order(
    val id: String = "",
    val status: String = "",
    val totalPrice: Int = 0,
    val paymentId: String = "",
    val paymentStatus: String = "",
    val paymentType: String = "",
    val therapy: Therapy = Therapy()
)
