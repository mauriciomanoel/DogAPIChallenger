package com.mauricio.dogapichallenger.di.module

import android.app.Application
import com.mauricio.dogapichallenger.breeds.repository.BreedsRepository
import com.mauricio.dogapichallenger.network.RetrofitApiService
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
    fun provideBreedsRepository(apiService: RetrofitApiService, application: Application) = BreedsRepository(apiService, application)

}