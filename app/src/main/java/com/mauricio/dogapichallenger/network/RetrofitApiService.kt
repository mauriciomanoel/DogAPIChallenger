package com.mauricio.dogapichallenger.network

import com.mauricio.dogapichallenger.breeds.BreedsResult
import retrofit2.http.*

interface RetrofitApiService {

    @GET("v1/breeds")
    suspend fun getBreeds(): BreedsResult

}