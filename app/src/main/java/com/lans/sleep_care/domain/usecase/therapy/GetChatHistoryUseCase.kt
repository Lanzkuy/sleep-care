package com.lans.sleep_care.domain.usecase.therapy

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.Chat
import kotlinx.coroutines.flow.Flow

interface GetChatHistoryUseCase {
    suspend fun execute(): Flow<Resource<List<Chat>>>
}