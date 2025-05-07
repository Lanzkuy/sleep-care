package com.lans.sleep_care.data.source.network.api

import com.lans.sleep_care.data.source.network.dto.request.ChatBotRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.ChatBotResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatBotApi {
    @POST("chat")
    suspend fun chat(
        @Body requestBody: ChatBotRequest
    ): ApiResponse<ChatBotResponse>
}