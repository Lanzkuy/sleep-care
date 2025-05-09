package com.lans.sleep_care.domain.usecase.auth

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.dto.request.VerifyOtpRequest
import kotlinx.coroutines.flow.Flow

interface VerifyOtpUseCase {
    suspend fun execute(otp: String, email: String): Flow<Resource<Boolean>>
}