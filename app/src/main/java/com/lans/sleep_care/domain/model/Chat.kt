package com.lans.sleep_care.domain.model

import com.lans.sleep_care.data.source.local.entity.ChatBotHistoryEntity

data class Chat(
    val sender: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)

fun Chat.toEntity() = ChatBotHistoryEntity(
    email = "",
    sender = sender,
    message = message,
    timestamp = timestamp
)
