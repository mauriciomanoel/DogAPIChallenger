package com.mauricio.dogapichallenger.di.module

import com.mauricio.dogapichallenger.di.scope.FragmentScope
import com.mauricio.dogapichallenger.ui.dogbreeds.DogBreedsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindings {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideExchangeRateFragment(): DogBreedsFragment
}
