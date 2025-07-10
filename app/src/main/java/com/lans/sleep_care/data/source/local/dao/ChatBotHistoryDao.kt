package com.lans.sleep_care.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lans.sleep_care.data.source.local.entity.ChatBotHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatBotHistoryDao {
    @Insert
    suspend fun create(entity: ChatBotHistoryEntity)

    @Query("SELECT * FROM chatbot_history WHERE email == :email")
    fun getAllHistories(email: String): Flow<List<ChatBotHistoryEntity>>
}