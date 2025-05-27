package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.network.dto.request.logbook.LogbookAnswerRequest
import com.lans.sleep_care.data.source.network.dto.request.logbook.LogbookQuestionRequest
import com.lans.sleep_care.data.source.network.dto.request.logbook.LogbookRequest
import com.lans.sleep_care.data.source.network.dto.request.logbook.SleepDiaryRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.logbook.LogbookQuestionListResponse
import com.lans.sleep_care.data.source.network.dto.response.logbook.LogbookAnswerListResponse
import com.lans.sleep_care.data.source.network.dto.response.logbook.SleepDiaryListResponse

interface ILogbookRepository {
    suspend fun fetchSleepDiaries(request: SleepDiaryRequest): ApiResponse<SleepDiaryListResponse>

    suspend fun fetchSleepDiaryDetail(
        sleepDiaryId: Int,
        request: SleepDiaryRequest
    ): ApiResponse<LogbookAnswerListResponse>

    suspend fun fetchQuestions(request: LogbookQuestionRequest): ApiResponse<LogbookQuestionListResponse>

    suspend fun fetchAnswers(request: LogbookAnswerRequest): ApiResponse<LogbookAnswerListResponse>

    suspend fun fetchAreas(): ApiResponse<List<String>>

    suspend fun createAnswer(request: LogbookRequest): ApiResponse<Any>

    suspend fun updateAnswer(request: LogbookRequest): ApiResponse<Any>
}