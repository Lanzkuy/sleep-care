package com.lans.sleep_care.data.source.network.api

import com.lans.sleep_care.data.source.network.dto.request.auth.LoginRequest
import com.lans.sleep_care.data.source.network.dto.request.auth.OtpRequest
import com.lans.sleep_care.data.source.network.dto.request.auth.OtpVerifyRequest
import com.lans.sleep_care.data.source.network.dto.request.auth.PasswordForgotRequest
import com.lans.sleep_care.data.source.network.dto.request.auth.PasswordResetRequest
import com.lans.sleep_care.data.source.network.dto.request.auth.RegisterRequest
import com.lans.sleep_care.data.source.network.dto.request.payment.PaymentRequest
import com.lans.sleep_care.data.source.network.dto.request.therapy.ChatRequest
import com.lans.sleep_care.data.source.network.dto.request.therapy.OrderTherapyRequest
import com.lans.sleep_care.data.source.network.dto.request.user.PasswordChangeRequest
import com.lans.sleep_care.data.source.network.dto.request.user.ProfileUpdateRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.chat.ChatListResponse
import com.lans.sleep_care.data.source.network.dto.response.chat.ChatResponse
import com.lans.sleep_care.data.source.network.dto.response.auth.LoginResponse
import com.lans.sleep_care.data.source.network.dto.response.payment.PaymentResponse
import com.lans.sleep_care.data.source.network.dto.response.psychologist.PsychologistListResponse
import com.lans.sleep_care.data.source.network.dto.response.psychologist.PsychologistResponse
import com.lans.sleep_care.data.source.network.dto.response.auth.RegisterResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.TherapyScheduleListResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.TherapyResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.OrderTherapyResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.TherapyScheduleResponse
import com.lans.sleep_care.data.source.network.dto.response.user.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface SleepCareApi {
    @POST("login")
    suspend fun login(
        @Body requestBody: LoginRequest
    ): ApiResponse<LoginResponse>

    @POST("register")
    suspend fun register(
        @Body requestBody: RegisterRequest
    ): ApiResponse<RegisterResponse>

    @POST("forgot-password")
    suspend fun forgotPassword(
        @Body requestBody: PasswordForgotRequest
    ): ApiResponse<Any>

    @POST("reset-password")
    suspend fun resetPassword(
        @Body requestBody: PasswordResetRequest
    ): ApiResponse<Any>

    @POST("otp/request")
    suspend fun requestOtp(
        @Body requestBody: OtpRequest
    ): ApiResponse<Any>

    @POST("otp/verify")
    suspend fun verifyOtp(
        @Body requestBody: OtpVerifyRequest
    ): ApiResponse<Any>

    @GET("patient/profile")
    suspend fun me(): ApiResponse<UserResponse>

    @PUT("patient/profile")
    suspend fun updateProfile(
        @Body requestBody: ProfileUpdateRequest
    ): ApiResponse<Any>

    @PUT("patient/password")
    suspend fun changePassword(
        @Body requestBody: PasswordChangeRequest
    ): ApiResponse<Any>

    @GET("doctors")
    suspend fun getAllPsychologist(
        @QueryMap requestParams: Map<String, @JvmSuppressWildcards Any>
    ): ApiResponse<PsychologistListResponse>

    @GET("doctors/{id}")
    suspend fun getPsychologist(
        @Path("id") id: Int
    ): ApiResponse<PsychologistResponse>

    @GET("therapies?status=in_progress")
    suspend fun getActiveTherapy(): ApiResponse<TherapyResponse>

    @GET("therapy/schedules/{id}")
    suspend fun getSchedules(
        @Path("id") id: Int
    ): ApiResponse<TherapyScheduleListResponse>

    @GET("therapy/chats")
    suspend fun getChatHistory(): ApiResponse<ChatListResponse>

    @POST("therapy/chats")
    suspend fun sendChat(
        @Body requestBody: ChatRequest
    ): ApiResponse<ChatResponse>

    @PUT("therapy/chats/{id}")
    suspend fun updateChat(
        @Path("id") id: Int
    ): ApiResponse<Any>

    @GET("therapy/orders?payment_status=pending")
    suspend fun getOrderStatus(): ApiResponse<OrderTherapyResponse>

    @POST("therapy/orders")
    suspend fun createOrder(
        @Body requestBody: OrderTherapyRequest
    ): ApiResponse<OrderTherapyResponse>

    @POST("midtrans/charge")
    suspend fun sendMidtransCharge(
        @Body requestBody: PaymentRequest
    ): PaymentResponse
}