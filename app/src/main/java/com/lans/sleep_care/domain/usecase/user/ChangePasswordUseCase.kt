package com.lans.sleep_care.domain.usecase.user

import com.lans.sleep_care.data.Resource
import kotlinx.coroutines.flow.Flow

interface ChangePasswordUseCase {
    suspend fun execute(
        currentPassword: String,
        newPassword: String,
        newPasswordConfirmation: String
    ): Flow<Resource<Boolean>>
}