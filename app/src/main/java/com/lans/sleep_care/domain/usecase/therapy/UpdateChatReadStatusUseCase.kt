package com.lans.sleep_care.domain.usecase.therapy

import com.lans.sleep_care.data.Resource
import kotlinx.coroutines.flow.Flow

interface UpdateChatReadStatusUseCase {
    suspend fun execute(chatId: Int): Flow<Resource<Boolean>>
}