package com.lans.sleep_care.domain.usecase.therapy

import com.lans.sleep_care.data.Resource
import kotlinx.coroutines.flow.Flow

interface CreateRatingUseCase {
    suspend fun execute(therapyId: Int, rating: Double, comment: String): Flow<Resource<Boolean>>
}