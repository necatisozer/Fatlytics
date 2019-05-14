package com.fatlytics.app.ui.addfood.fooddetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatlytics.app.data.source.api.FatlyticsApi
import com.fatlytics.app.data.source.api.FatlyticsManager
import com.fatlytics.app.data.source.api.Food
import com.fatlytics.app.extension.doInBackground
import com.fatlytics.app.helper.SingleLiveEvent
import com.fatlytics.app.ui.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class FoodDetailViewModel @Inject constructor(
    private val fatlyticsManager: FatlyticsManager,
    private val fatlyticsApi: FatlyticsApi
) : BaseViewModel() {
    private val mFood = MutableLiveData<Food>()
    val food: LiveData<Food> get() = mFood

    private val mFoodAddCompleteEvent = SingleLiveEvent<Void>()
    val foodAddCompleteEvent: LiveData<Void> get() = mFoodAddCompleteEvent

    fun getFood(foodId: Long, amount: Double) {
        fatlyticsManager.getFood(foodId, amount).doInBackground().subscribeBy {
            mFood.value = it
        }.also { disposables += it }
    }

    fun addFood(
        foodId: Long,
        entryName: String,
        servingId: Long,
        numberOfUnits: Double,
        meal: String
    ) {
        fatlyticsApi.createFoodEntry(foodId, entryName, servingId, numberOfUnits, meal)
            .doInBackground()
            .subscribeBy { mFoodAddCompleteEvent.call() }
            .also { disposables += it }
    }
}