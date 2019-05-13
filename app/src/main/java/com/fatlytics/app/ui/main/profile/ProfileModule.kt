package com.fatlytics.app.ui.main.profile

import androidx.lifecycle.ViewModel
import com.fatlytics.app.ui.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ProfileModule {
    @ContributesAndroidInjector
    abstract fun bindProfileFragment(): ProfileFragment

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel
}
