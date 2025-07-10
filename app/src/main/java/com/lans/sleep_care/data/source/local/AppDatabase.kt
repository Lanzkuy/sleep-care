package com.lans.sleep_care.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lans.sleep_care.data.source.local.dao.ChatBotHistoryDao
import com.lans.sleep_care.data.source.local.entity.ChatBotHistoryEntity

@Database(
    entities = [ChatBotHistoryEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val chatBotHistoryDao: ChatBotHistoryDao
}