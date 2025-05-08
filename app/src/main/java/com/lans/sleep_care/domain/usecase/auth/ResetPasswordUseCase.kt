package com.lans.sleep_care.domain.usecase.auth

import com.lans.sleep_care.data.Resource
import kotlinx.coroutines.flow.Flow

interface ResetPasswordUseCase {
    suspend fun execute(
        email: String,
        token: Int,
        password: String,
        passwordConfirmation: String
    ): Flow<Resource<Boolean>>
}