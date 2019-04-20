package com.fatlytics.app.ui.registration

import androidx.lifecycle.ViewModel
import com.fatlytics.app.ui.ViewModelKey
import com.fatlytics.app.ui.registration.personalinfo.PersonalInfoModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [PersonalInfoModule::class])
abstract class RegistrationModule {
    @ContributesAndroidInjector
    abstract fun bindRegistrationActivity(): RegistrationActivity

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    abstract fun bindRegistrationViewModel(registrationViewModel: RegistrationViewModel): ViewModel
}
