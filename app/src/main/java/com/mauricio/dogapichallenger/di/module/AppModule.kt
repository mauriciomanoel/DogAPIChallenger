package com.mauricio.dogapichallenger.di.module

import android.app.Application
import android.content.Context
import com.mauricio.dogapichallenger.breeds.repository.BreedsRepository
import com.mauricio.dogapichallenger.network.RetrofitApiService
import com.mauricio.dogapichallenger.breeds.viewmodel.DogBreedsViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideCartRepository(apiService: RetrofitApiService, application: Application) = BreedsRepository(apiService, application)

    @Provides
    fun provideDogBreedsViewModel(application: Application) = DogBreedsViewModel(application)
}
