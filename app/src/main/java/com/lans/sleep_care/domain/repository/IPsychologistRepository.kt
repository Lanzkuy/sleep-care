package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.PsychologistListResponse
import com.lans.sleep_care.data.source.network.dto.response.PsychologistResponse

interface IPsychologistRepository {
    suspend fun fetchAllPsychologist(): ApiResponse<PsychologistListResponse>
    suspend fun fetchPsychologist(id: Int): ApiResponse<PsychologistResponse>
}