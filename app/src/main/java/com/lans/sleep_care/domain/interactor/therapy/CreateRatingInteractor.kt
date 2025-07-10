package com.lans.sleep_care.domain.interactor.therapy

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.therapy.RatingTherapyRequest
import com.lans.sleep_care.domain.repository.ITherapyRepository
import com.lans.sleep_care.domain.usecase.therapy.CreateRatingUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CreateRatingInteractor @Inject constructor(
    private val repository: ITherapyRepository
) : CreateRatingUseCase, SafeApiCall {
    override suspend fun execute(
        therapyId: Int,
        rating: Double,
        comment: String
    ): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    val response = repository.createRating(
                        RatingTherapyRequest(
                            therapyId = therapyId,
                            rating = rating,
                            comment = comment
                        )
                    ).message
                    response == "Berhasil memberikan rating."
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}