package com.mauricio.dogapichallenger.network

import com.mauricio.dogapichallenger.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HttpHeadersInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request?.newBuilder()
            ?.addHeader("Content-Type", "application/json;charset=UTF-8")
            ?.addHeader("x-api-key", BuildConfig.API_KEY)
            ?.build()
        return chain.proceed(request)
    }
}