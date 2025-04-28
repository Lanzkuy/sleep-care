package com.lans.sleep_care.domain.model

data class ThoughtRecord(
    val date: String,
    val situation: String,
    val thoughts: List<String>
)
