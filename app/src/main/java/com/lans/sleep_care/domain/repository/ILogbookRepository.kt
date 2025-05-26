package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.network.dto.request.logbook.LogbookQuestionRequest
import com.lans.sleep_care.data.source.network.dto.request.logbook.LogbookRequest
import com.lans.sleep_care.data.source.network.dto.request.logbook.SleepDiaryRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.logbook.LogbookQuestionListResponse
import com.lans.sleep_care.data.source.network.dto.response.logbook.SleepDiaryDetailResponse
import com.lans.sleep_care.data.source.network.dto.response.logbook.SleepDiaryListResponse

interface ILogbookRepository {
    suspend fun fetchSleepDiaries(request: SleepDiaryRequest): ApiResponse<SleepDiaryListResponse>

    suspend fun fetchSleepDiaryDetail(
        sleepDiaryId: Int,
        request: SleepDiaryRequest
    ): ApiResponse<SleepDiaryDetailResponse>

    suspend fun fetchQuestions(request: LogbookQuestionRequest): ApiResponse<LogbookQuestionListResponse>

    suspend fun createAnswer(request: LogbookRequest): ApiResponse<Any>

    suspend fun updateAnswer(request: LogbookRequest): ApiResponse<Any>
}