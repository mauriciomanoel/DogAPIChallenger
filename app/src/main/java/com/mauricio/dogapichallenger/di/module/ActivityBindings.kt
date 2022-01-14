package com.mauricio.dogapichallenger.di.module

import com.mauricio.dogapichallenger.MainActivity
import com.mauricio.dogapichallenger.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindings {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}
