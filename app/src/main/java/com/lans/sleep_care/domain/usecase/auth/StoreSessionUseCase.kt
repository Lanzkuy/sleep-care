package com.lans.sleep_care.domain.usecase.auth

interface StoreSessionUseCase {
    suspend fun invoke(accessToken: String)
}
