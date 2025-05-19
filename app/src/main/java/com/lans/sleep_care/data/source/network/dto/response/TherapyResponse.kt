package com.lans.sleep_care.data.source.network.dto.response

import com.lans.sleep_care.domain.model.Therapy
import com.squareup.moshi.Json

data class TherapyResponse(
    val id: Int,
    @field:Json(name = "doctor_id")
    val doctorId: Int,
    @field:Json(name = "patient_id")
    val patientId: Int,
    @field:Json(name = "start_date")
    val startDate: String,
    @field:Json(name = "end_date")
    val endDate: String,
    val status: String,
    @field:Json(name = "doctor_fee")
    val doctorFee: Int,
    @field:Json(name = "application_fee")
    val applicationFee: Int,
    val comment: String?
)

fun TherapyResponse.toDomain() = Therapy(
    id = id,
    doctorId = doctorId,
    patientId = patientId,
    startDate = startDate,
    endDate = endDate,
    status = status,
    doctorFee = doctorFee,
    applicationFee = applicationFee,
    comment = comment ?: ""
)