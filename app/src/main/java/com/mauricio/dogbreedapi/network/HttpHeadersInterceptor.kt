package com.mauricio.dogbreedapi.network

import com.mauricio.dogbreedapi.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HttpHeadersInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json;charset=UTF-8")
            .addHeader("x-api-key", BuildConfig.API_KEY)
            .build()
        return chain.proceed(request)
    }
}