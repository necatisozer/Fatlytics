package com.fatlytics.app.ui

import androidx.lifecycle.ViewModelProvider
import com.fatlytics.app.ui.main.MainModule
import com.fatlytics.app.ui.splash.SplashModule
import dagger.Binds
import dagger.Module

@Module(includes = [SplashModule::class, MainModule::class])
abstract class ActivityModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}