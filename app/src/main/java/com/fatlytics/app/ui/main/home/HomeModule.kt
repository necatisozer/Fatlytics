package com.fatlytics.app.ui.main.home

import androidx.lifecycle.ViewModel
import com.fatlytics.app.ui.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class HomeModule {
    @ContributesAndroidInjector
    abstract fun bindHomeFragment(): HomeFragment

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel
}
