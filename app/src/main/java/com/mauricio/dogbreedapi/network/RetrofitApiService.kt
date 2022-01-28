package com.mauricio.dogbreedapi.network

import com.mauricio.dogbreedapi.breeds.models.BreedsByIdResult
import com.mauricio.dogbreedapi.breeds.models.BreedsResult
import retrofit2.http.*

interface RetrofitApiService {
    @GET("v1/breeds")
    suspend fun getBreeds(): BreedsResult

    @GET("v1/images/search")
    suspend fun getBreedsById(@Query("breed_id") breedId: String, @Query("limit") limit: Int = 100): BreedsByIdResult
}