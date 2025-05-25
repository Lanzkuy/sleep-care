package com.lans.sleep_care.domain.usecase.psychologist

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.therapy.Psychologist
import kotlinx.coroutines.flow.Flow

interface GetAllPsychologistUseCase {
    suspend fun execute(page: Int): Flow<Resource<List<Psychologist>>>
}