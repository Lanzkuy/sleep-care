package com.lans.sleep_care.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lans.sleep_care.domain.model.Chat

@Entity(tableName = "chatbot_history")
data class ChatBotHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "email")
    val email: String,
    val sender: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)

fun ChatBotHistoryEntity.toDomain() = Chat(
    sender = sender,
    message = message,
    timestamp = timestamp
)
