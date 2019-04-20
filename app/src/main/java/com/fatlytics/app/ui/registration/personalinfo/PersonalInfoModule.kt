package com.fatlytics.app.ui.registration.personalinfo

import androidx.lifecycle.ViewModel
import com.fatlytics.app.ui.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PersonalInfoModule {
    @ContributesAndroidInjector
    abstract fun bindPersonalInfoFragment(): PersonalInfoFragment

    @Binds
    @IntoMap
    @ViewModelKey(PersonalInfoViewModel::class)
    abstract fun bindPersonalInfoViewModel(personalInfoViewModel: PersonalInfoViewModel): ViewModel
}
