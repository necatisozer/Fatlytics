package com.fatlytics.app.ui.registration.healthinfo

import androidx.lifecycle.ViewModel
import com.fatlytics.app.ui.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class HealthInfoModule {
    @ContributesAndroidInjector
    abstract fun bindHealthInfoFragment(): HealthInfoFragment

    @Binds
    @IntoMap
    @ViewModelKey(HealthInfoViewModel::class)
    abstract fun bindHealthInfoViewModel(healthInfoViewModel: HealthInfoViewModel): ViewModel
}
