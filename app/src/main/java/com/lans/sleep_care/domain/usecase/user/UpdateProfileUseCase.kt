package com.lans.sleep_care.domain.usecase.user

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UpdateProfileUseCase {
    suspend fun execute(user: User): Flow<Resource<Boolean>>
}