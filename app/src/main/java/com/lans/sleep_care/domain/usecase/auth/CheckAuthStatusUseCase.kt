package com.lans.sleep_care.domain.usecase.auth

import com.lans.sleep_care.data.Resource
import kotlinx.coroutines.flow.Flow

interface CheckAuthStatusUseCase {
    suspend fun execute(): Flow<Resource<Boolean>>
}