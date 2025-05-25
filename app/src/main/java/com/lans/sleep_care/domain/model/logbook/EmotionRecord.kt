package com.lans.sleep_care.domain.model.logbook

data class EmotionRecord(
    val date: String,
    val emotion: String,
    val intensity: Int,
    val situation: String? = null,
    val thoughts: String? = null,
    val copingStrategy: String? = null,
    val emotionAfter: String? = null,
    val intensityAfter: Int? = null
)