package com.fatlytics.app.ui.addfood

import androidx.lifecycle.ViewModel
import com.fatlytics.app.ui.ViewModelKey
import com.fatlytics.app.ui.addfood.fooddetail.FoodDetailModule
import com.fatlytics.app.ui.addfood.search.SearchModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [SearchModule::class, FoodDetailModule::class])
abstract class AddFoodModule {
    @ContributesAndroidInjector
    abstract fun bindAddFoodActivity(): AddFoodActivity

    @Binds
    @IntoMap
    @ViewModelKey(AddFoodViewModel::class)
    abstract fun bindAddFoodViewModel(addFoodViewModel: AddFoodViewModel): ViewModel
}
