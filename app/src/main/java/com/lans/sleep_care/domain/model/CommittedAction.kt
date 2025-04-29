package com.lans.sleep_care.domain.model

data class CommittedAction(
    val area: String,
    val goal: String,
    val plan: String,
    val time: String,
    val status: String,
    val obstacle: String,
    val solution: String
)
