package com.mauricio.dogbreedapi.di.module

import com.mauricio.dogbreedapi.breeds.repository.BreedDao
import com.mauricio.dogbreedapi.breeds.repository.BreedsRepository
import com.mauricio.dogbreedapi.network.RetrofitApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideBreedsRepository(apiService: RetrofitApiService, breedDao: BreedDao) = BreedsRepository(apiService, breedDao)
}