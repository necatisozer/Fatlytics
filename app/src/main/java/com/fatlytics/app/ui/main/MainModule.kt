package com.fatlytics.app.ui.main

import androidx.lifecycle.ViewModel
import com.fatlytics.app.ui.ViewModelKey
import com.fatlytics.app.ui.main.diary.DiaryModule
import com.fatlytics.app.ui.main.home.HomeModule
import com.fatlytics.app.ui.main.profile.ProfileModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [HomeModule::class, DiaryModule::class, ProfileModule::class])
abstract class MainModule {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel
}
