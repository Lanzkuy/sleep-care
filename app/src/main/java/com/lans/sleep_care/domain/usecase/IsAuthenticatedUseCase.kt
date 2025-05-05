package com.lans.sleep_care.domain.usecase

import com.lans.sleep_care.data.Resource
import kotlinx.coroutines.flow.Flow

interface IsAuthenticatedUseCase {
    suspend fun execute(): Flow<Resource<Boolean>>
}