package com.lans.sleep_care.data.repository

import com.lans.sleep_care.data.source.network.api.SleepCareApi
import com.lans.sleep_care.data.source.network.dto.request.psychologist.PsychologistListRequest
import com.lans.sleep_care.data.source.network.dto.request.psychologist.toQueryMap
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.PsychologistListResponse
import com.lans.sleep_care.data.source.network.dto.response.PsychologistResponse
import com.lans.sleep_care.domain.repository.IPsychologistRepository
import javax.inject.Inject

class PsychologistRepository @Inject constructor(
    private val api: SleepCareApi
) : IPsychologistRepository {
    override suspend fun fetchAllPsychologist(request: PsychologistListRequest): ApiResponse<PsychologistListResponse> {
        return api.getAllPsychologist(request.toQueryMap())
    }

    override suspend fun fetchPsychologist(id: Int): ApiResponse<PsychologistResponse> {
        return api.getPsychologist(id)
    }
}