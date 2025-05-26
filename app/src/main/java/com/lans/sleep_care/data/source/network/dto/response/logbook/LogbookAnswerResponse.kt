package com.lans.sleep_care.data.source.network.dto.response.logbook

import com.lans.sleep_care.domain.model.logbook.LogbookAnswer

data class LogbookAnswerResponse(
    val id: Int,
    val type: String,
    val answer: String,
    val note: String,
)

fun LogbookAnswerResponse.toDomain() = LogbookAnswer(
    id = id,
    type = type,
    answer = answer,
    note = note
)