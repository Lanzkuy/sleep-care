package com.lans.sleep_care.domain.model

data class Chat(
    val id: Int = 0,
    val therapyId: Int = 0,
    val senderId: Int = 0,
    val receiverId: Int = 0,
    val message: String = "",
    val readAt: String = ""
)
