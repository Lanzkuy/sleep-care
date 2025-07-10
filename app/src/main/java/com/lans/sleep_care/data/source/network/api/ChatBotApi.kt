package com.lans.sleep_care.data.source.network.api

import com.lans.sleep_care.data.source.network.dto.request.chatbot.ChatBotRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.chatbot.ChatBotResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatBotApi {
    @POST("chat")
    suspend fun chat(
        @Body requestBody: ChatBotRequest
    ): ApiResponse<ChatBotResponse>
}