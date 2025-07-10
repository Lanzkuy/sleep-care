package com.lans.sleep_care.domain.usecase.user

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.auth.User
import kotlinx.coroutines.flow.Flow

interface GetUserProfileUseCase {
    suspend fun execute(): Flow<Resource<User>>
}