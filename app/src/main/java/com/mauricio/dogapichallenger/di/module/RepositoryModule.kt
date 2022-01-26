package com.mauricio.dogapichallenger.di.module

import android.app.Application
import com.mauricio.dogapichallenger.breeds.repository.BreedDao
import com.mauricio.dogapichallenger.breeds.repository.BreedRoomDB
import com.mauricio.dogapichallenger.breeds.repository.BreedsRepository
import com.mauricio.dogapichallenger.network.RetrofitApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    private val applicationScope = CoroutineScope(SupervisorJob())

    @Provides
    fun provideDatabase(application: Application) = BreedRoomDB.getDatabase(application).breedDao()

    @Provides
    @ViewModelScoped
    fun provideBreedsRepository(apiService: RetrofitApiService, breedDao: BreedDao) = BreedsRepository(apiService, breedDao)
}