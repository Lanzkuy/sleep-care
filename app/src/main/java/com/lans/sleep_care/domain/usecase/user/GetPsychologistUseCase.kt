package com.lans.sleep_care.domain.usecase.user

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.Psychologist
import kotlinx.coroutines.flow.Flow

interface GetPsychologistUseCase {
    suspend fun execute(id: Int): Flow<Resource<Psychologist>>
}