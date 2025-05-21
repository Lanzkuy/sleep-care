package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.network.dto.request.psychologist.PsychologistListRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.psychologist.PsychologistListResponse
import com.lans.sleep_care.data.source.network.dto.response.psychologist.PsychologistResponse

interface IPsychologistRepository {
    suspend fun fetchAllPsychologist(request: PsychologistListRequest): ApiResponse<PsychologistListResponse>
    suspend fun fetchPsychologist(id: Int): ApiResponse<PsychologistResponse>
}