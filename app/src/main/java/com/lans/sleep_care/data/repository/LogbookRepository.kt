package com.lans.sleep_care.data.repository

import com.lans.sleep_care.data.source.network.api.SleepCareApi
import com.lans.sleep_care.data.source.network.dto.request.logbook.LogbookAnswerRequest
import com.lans.sleep_care.data.source.network.dto.request.logbook.LogbookQuestionRequest
import com.lans.sleep_care.data.source.network.dto.request.logbook.LogbookRequest
import com.lans.sleep_care.data.source.network.dto.request.logbook.SleepDiaryRequest
import com.lans.sleep_care.data.source.network.dto.request.logbook.toQueryMap
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.logbook.LogbookAnswerListResponse
import com.lans.sleep_care.data.source.network.dto.response.logbook.LogbookQuestionListResponse
import com.lans.sleep_care.data.source.network.dto.response.logbook.SleepDiaryListResponse
import com.lans.sleep_care.domain.repository.ILogbookRepository
import javax.inject.Inject

class LogbookRepository @Inject constructor(
    private val api: SleepCareApi
) : ILogbookRepository {
    override suspend fun fetchSleepDiaries(request: SleepDiaryRequest): ApiResponse<SleepDiaryListResponse> {
        return api.getSleepDiaries(request.toQueryMap())
    }

    override suspend fun fetchSleepDiaryDetail(
        sleepDiaryId: Int,
        request: SleepDiaryRequest
    ): ApiResponse<LogbookAnswerListResponse> {
        return api.getSleepDiaryDetail(sleepDiaryId, request.toQueryMap())
    }

    override suspend fun fetchQuestions(request: LogbookQuestionRequest): ApiResponse<LogbookQuestionListResponse> {
        return api.getQuestions(request.toQueryMap())
    }

    override suspend fun fetchAnswers(request: LogbookAnswerRequest): ApiResponse<LogbookAnswerListResponse> {
        return api.getAnswers(request.toQueryMap())
    }

    override suspend fun fetchAreas(): ApiResponse<List<String>> {
        return api.getAreas()
    }

    override suspend fun createAnswer(request: LogbookRequest): ApiResponse<Any> {
        return api.createAnswer(request)
    }

    override suspend fun updateAnswer(request: LogbookRequest): ApiResponse<Any> {
        return api.updateAnswer(request)
    }
}