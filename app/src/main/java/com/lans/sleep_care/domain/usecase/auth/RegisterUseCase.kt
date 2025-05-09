package com.lans.sleep_care.domain.usecase.auth

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.User
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {
    suspend fun execute(
        user: User,
        password: String,
        passwordConfirmation: String
    ): Flow<Resource<User>>
}