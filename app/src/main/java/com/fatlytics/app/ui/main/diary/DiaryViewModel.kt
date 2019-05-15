package com.fatlytics.app.ui.main.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatlytics.app.data.source.api.FatlyticsApi
import com.fatlytics.app.data.source.api.FatlyticsManager
import com.fatlytics.app.data.source.api.FoodEntriesModel
import com.fatlytics.app.extension.doInBackground
import com.fatlytics.app.ui.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class DiaryViewModel @Inject constructor(
    private val fatlyticsManager: FatlyticsManager,
    private val fatlyticsApi: FatlyticsApi
) : BaseViewModel() {
    private val mBreakfastFoodEntries = MutableLiveData<FoodEntriesModel>()
    val breakfastFoodEntries: LiveData<FoodEntriesModel> get() = mBreakfastFoodEntries

    fun init() {
        fatlyticsManager.getFoodEntries()
            .doInBackground()
            .subscribeBy { mBreakfastFoodEntries.value = it }
            .also { disposables += it }
    }

    fun deleteFoodEntry(foodEntryId: Long) {
        fatlyticsApi.deleteFoodEntry(foodEntryId)
            .doInBackground()
            .subscribeBy { init() }
            .also { disposables += it }
    }
}



