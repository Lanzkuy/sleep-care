package com.lans.sleep_care.data.source.network.interceptor

import com.lans.sleep_care.common.Constant
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(name = Constant.HEADER_ACCEPT, value = Constant.HEADER_ACCEPT_VALUE)
            .build()
        return chain.proceed(request)
    }
}