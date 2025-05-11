package com.lans.sleep_care.data.repository

import com.lans.sleep_care.data.source.network.api.SleepCareApi
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.PsychologistListResponse
import com.lans.sleep_care.data.source.network.dto.response.PsychologistResponse
import com.lans.sleep_care.domain.repository.IPsychologistRepository
import javax.inject.Inject

class PsychologistRepository @Inject constructor(
    private val api: SleepCareApi
): IPsychologistRepository {
    override suspend fun fetchAllPsychologist(): ApiResponse<PsychologistListResponse> {
        return api.getAllPsychologist()
    }

    override suspend fun fetchPsychologist(id: Int): ApiResponse<PsychologistResponse> {
        return api.getPsychologist(id)
    }
}