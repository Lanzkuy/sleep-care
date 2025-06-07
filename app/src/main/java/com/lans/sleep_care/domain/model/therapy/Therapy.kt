package com.lans.sleep_care.domain.model.therapy

data class Therapy(
    val id: Int = 0,
    val doctorId: Int = 0,
    val patientId: Int = 0,
    val startDate: String = "",
    val endDate: String = "",
    val status: String = "",
    val doctorFee: Int = 0,
    val applicationFee: Int = 0,
    val comment: String = "",
    val rating: Double = 0.0
)
