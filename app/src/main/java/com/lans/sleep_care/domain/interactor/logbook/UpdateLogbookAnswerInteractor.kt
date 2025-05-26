package com.lans.sleep_care.domain.interactor.logbook

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.logbook.LogbookAnswerRequest
import com.lans.sleep_care.data.source.network.dto.request.logbook.LogbookRequest
import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer
import com.lans.sleep_care.domain.repository.ILogbookRepository
import com.lans.sleep_care.domain.usecase.logbook.UpdateLogbookAnswerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdateLogbookAnswerInteractor @Inject constructor(
    private val repository: ILogbookRepository
) : UpdateLogbookAnswerUseCase, SafeApiCall {
    override suspend fun execute(
        therapyId: Int,
        recordId: Int,
        recordType: String,
        questionAnswers: List<LogbookQuestionAnswer>
    ): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    val response = repository.updateAnswer(
                        LogbookRequest(
                            therapyId = therapyId,
                            recordId = recordId,
                            recordType = recordType,
                            answers = questionAnswers.map {
                                LogbookAnswerRequest(
                                    questionId = it.questionId,
                                    id = it.answer.id,
                                    type = it.answer.type,
                                    answer = it.answer.answer,
                                    note = it.answer.note
                                )
                            }
                        )
                    ).message
                    response == "Jawaban berhasil diubah."
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}