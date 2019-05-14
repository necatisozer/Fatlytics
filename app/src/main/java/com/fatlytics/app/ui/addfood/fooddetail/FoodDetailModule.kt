package com.fatlytics.app.ui.addfood.fooddetail

import androidx.lifecycle.ViewModel
import com.fatlytics.app.ui.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FoodDetailModule {
    @ContributesAndroidInjector
    abstract fun bindFoodDetailFragment(): FoodDetailFragment

    @Binds
    @IntoMap
    @ViewModelKey(FoodDetailViewModel::class)
    abstract fun bindFoodDetailViewModel(foodDetailViewModel: FoodDetailViewModel): ViewModel
}
