package com.lans.sleep_care.domain.usecase.auth

interface SaveSessionUseCase {
    suspend fun invoke(accessToken: String)
}
