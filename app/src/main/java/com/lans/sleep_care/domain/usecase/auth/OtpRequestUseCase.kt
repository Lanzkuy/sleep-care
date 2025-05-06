package com.lans.sleep_care.domain.usecase.auth

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.dto.request.OtpRequest
import kotlinx.coroutines.flow.Flow

interface OtpRequestUseCase {
    suspend fun execute(request: OtpRequest): Flow<Resource<Boolean>>
}