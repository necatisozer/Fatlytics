package com.fatlytics.app.ui.main.diary

import androidx.lifecycle.ViewModel
import com.fatlytics.app.ui.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class DiaryModule {
    @ContributesAndroidInjector
    abstract fun bindDiaryFragment(): DiaryFragment

    @Binds
    @IntoMap
    @ViewModelKey(DiaryViewModel::class)
    abstract fun bindDiaryViewModel(diaryViewModel: DiaryViewModel): ViewModel
}
