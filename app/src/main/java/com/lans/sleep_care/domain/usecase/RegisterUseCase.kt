package com.lans.sleep_care.domain.usecase

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.dto.request.RegisterRequest
import com.lans.sleep_care.domain.model.User
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {
    suspend fun execute(request: RegisterRequest): Flow<Resource<User>>
}