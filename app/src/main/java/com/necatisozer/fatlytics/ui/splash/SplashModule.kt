package com.necatisozer.fatlytics.ui.splash

import androidx.lifecycle.ViewModel
import com.necatisozer.fatlytics.ui.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SplashModule {
    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel
}