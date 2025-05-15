package com.lans.sleep_care.data.source.network.dto.response

import com.lans.sleep_care.domain.model.Chat
import com.lans.sleep_care.domain.model.ChatBot
import com.squareup.moshi.Json

data class ChatResponse(
    val id: Int,
    @field:Json(name = "therapy_id")
    val therapyId: Int,
    @field:Json(name = "sender_id")
    val senderId: Int,
    @field:Json(name = "receiver_id")
    val receiverId: Int,
    val message: String,
    @field:Json(name = "read_at")
    val readAt: String?
)

fun ChatResponse.toDomain() = Chat(
    id = id,
    therapyId = therapyId,
    senderId = senderId,
    receiverId = receiverId,
    message = message,
    readAt = readAt ?: ""
)
