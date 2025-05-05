package com.lans.sleep_care.domain.usecase

interface StoreSessionUseCase {
    suspend fun invoke(accessToken: String)
}
