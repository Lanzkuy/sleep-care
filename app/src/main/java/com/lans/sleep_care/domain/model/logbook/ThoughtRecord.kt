package com.lans.sleep_care.domain.model.logbook

data class ThoughtRecord(
    val date: String,
    val situation: String,
    val thoughts: List<String>
)
