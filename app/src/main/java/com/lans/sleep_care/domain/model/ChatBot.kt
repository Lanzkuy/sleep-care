package com.lans.sleep_care.domain.model

import com.lans.sleep_care.data.source.local.entity.ChatBotHistoryEntity

data class ChatBot(
    val id:Int = 0,
    val therapyId: Int= 0,
    val sender: String = "",
    val receiver: String = "",
    val message: String = "",
    val readAt: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

fun ChatBot.toEntity() = ChatBotHistoryEntity(
    email = "",
    sender = sender,
    message = message,
    timestamp = timestamp
)
