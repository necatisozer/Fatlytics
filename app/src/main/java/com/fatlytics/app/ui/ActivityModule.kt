package com.fatlytics.app.ui

import androidx.lifecycle.ViewModelProvider
import com.fatlytics.app.ui.addfood.AddFoodModule
import com.fatlytics.app.ui.main.MainModule
import com.fatlytics.app.ui.registration.RegistrationModule
import com.fatlytics.app.ui.splash.SplashModule
import dagger.Binds
import dagger.Module

@Module(includes = [SplashModule::class, RegistrationModule::class, MainModule::class, AddFoodModule::class])
abstract class ActivityModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}