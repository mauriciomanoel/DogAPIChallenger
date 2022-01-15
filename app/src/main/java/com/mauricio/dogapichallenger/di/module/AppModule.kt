package com.mauricio.dogapichallenger.di.module

import android.app.Application
import com.mauricio.dogapichallenger.breeds.repository.BreedsRepository
import com.mauricio.dogapichallenger.network.RetrofitApiService
import com.mauricio.dogapichallenger.breeds.viewmodel.DogBreedsViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideCartRepository(apiService: RetrofitApiService) = BreedsRepository(apiService)
//
//    @Provides
//    @Singleton
//    fun provideExchangeRepository(apiService: RetrofitApiService, application: Application) = ExchangeRepository(apiService, application)
//
    @Provides
    fun provideDogBreedsViewModel(application: Application) = DogBreedsViewModel(application)
//
//    @Provides
//    fun provideCartViewModel(application: Application) = CartViewModel(application)
//
//    @Provides
//    fun provideExchangeViewModel(application: Application) = ExchangeViewModel(application)
}
