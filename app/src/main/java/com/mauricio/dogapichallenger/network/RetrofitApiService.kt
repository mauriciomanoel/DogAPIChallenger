package com.mauricio.dogapichallenger.network

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface RetrofitApiService {

    @GET("v1/breeds")
    fun getExchangeRates(@Query("access_key") accessKey: String): Observable<Response<String>>

    @GET
    fun setNetworkStats(@Url url: String, @Query("action") action: String, @Query("duration") duration: Long, @Query("status") status: String): Observable<Response<String>>
}