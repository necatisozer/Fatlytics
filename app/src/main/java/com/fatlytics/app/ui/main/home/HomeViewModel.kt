package com.fatlytics.app.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatlytics.app.data.source.api.FatlyticsManager
import com.fatlytics.app.data.source.api.FoodEntriesModel
import com.fatlytics.app.extension.doInBackground
import com.fatlytics.app.ui.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val fatlyticsManager: FatlyticsManager
) : BaseViewModel() {
    private val mFoodEntries = MutableLiveData<FoodEntriesModel>()
    val foodEntries: LiveData<FoodEntriesModel> get() = mFoodEntries

    init {
        fatlyticsManager.getFoodEntries()
            .doInBackground()
            .subscribeBy(
                onSuccess = { mFoodEntries.value = it },
                onError = { mUnexpectedErrorEvent.call() })
            .also { disposables += it }
    }
}