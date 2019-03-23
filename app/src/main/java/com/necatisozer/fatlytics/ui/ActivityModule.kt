package com.necatisozer.fatlytics.ui

import androidx.lifecycle.ViewModelProvider
import com.necatisozer.fatlytics.ui.main.MainModule
import com.necatisozer.fatlytics.ui.splash.SplashModule
import dagger.Binds
import dagger.Module

@Module(includes = [SplashModule::class, MainModule::class])
abstract class ActivityModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}