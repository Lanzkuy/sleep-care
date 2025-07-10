package com.lans.sleep_care.domain.usecase.auth

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.auth.Session
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    suspend fun execute(email: String, password: String): Flow<Resource<Session>>
}