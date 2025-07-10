package com.lans.sleep_care.data.source.network.interceptor

import com.lans.sleep_care.common.Constant.HEADER_AUTHORIZATION
import com.lans.sleep_care.common.Constant.TOKEN_TYPE
import com.lans.sleep_care.data.source.local.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking {
            dataStoreManager.getAccessToken().first()
        }

        val request = chain.request().newBuilder()
            .addHeader(name = HEADER_AUTHORIZATION, value = "$TOKEN_TYPE $accessToken")
            .build()

        return chain.proceed(request)
    }
}