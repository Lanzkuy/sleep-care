package com.lans.sleep_care.domain.usecase

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.dto.request.LoginRequest
import com.lans.sleep_care.domain.model.User
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    suspend fun execute(loginRequest: LoginRequest): Flow<Resource<User>>
}